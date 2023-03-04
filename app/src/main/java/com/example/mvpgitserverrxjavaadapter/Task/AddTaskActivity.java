package com.example.mvpgitserverrxjavaadapter.Task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mvpgitserverrxjavaadapter.Day.AddTaskPresenter;
import com.example.mvpgitserverrxjavaadapter.Model.Days;
import com.example.mvpgitserverrxjavaadapter.Model.MainApiService;
import com.example.mvpgitserverrxjavaadapter.Model.Task;
import com.example.mvpgitserverrxjavaadapter.R;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddTaskActivity extends AppCompatActivity implements TaskContract.AddTaskView {
    private TextInputEditText edtTitle;
    private View saveBtn;
    private MainApiService mainApiService;
    private Days days;
    private TaskContract.AddTaskPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        edtTitle=findViewById(R.id.edtTaskAddTask);

        mainApiService=new MainApiService();
        presenter=new AddTaskPresenter();

        days=getIntent().getParcelableExtra("days");
        saveBtn=findViewById(R.id.saveTaskAddTaskActivity);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainApiService.addTask(days.getDayId(),edtTitle.getText().toString())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Task>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(Task task) {
                                presenter.saveChange(task);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            }
        });


      presenter.onAttach(this);
    }

    @Override
    public void changeActivity(int resultCode, Task task) {
        Intent intent=new Intent();
        intent.putExtra("xTask",task);
        setResult(resultCode,intent);
        finish();
    }
}