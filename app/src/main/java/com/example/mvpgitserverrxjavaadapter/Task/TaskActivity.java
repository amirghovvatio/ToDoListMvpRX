package com.example.mvpgitserverrxjavaadapter.Task;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mvpgitserverrxjavaadapter.Model.Days;
import com.example.mvpgitserverrxjavaadapter.Model.MainApiService;
import com.example.mvpgitserverrxjavaadapter.Model.Task;
import com.example.mvpgitserverrxjavaadapter.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskActivity extends AppCompatActivity implements TaskAdapter.TaskEventListener,TaskContract.View{
    private TaskAdapter adapter;
    private RecyclerView rvTaskList;
    private MainApiService apiService;
    private Days days;
    private TaskContract.Presenter presenter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout mTask;
    private View addTaskBtn;
    public static final int RESULT_CODE=230;
    public static final int  REQUEST_CODE= 164;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        addTaskBtn=findViewById(R.id.addTaskBtn);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addTaskClicked();
            }
        });
        apiService=new MainApiService();
        mTask=findViewById(R.id.linearTask);
        shimmerFrameLayout=findViewById(R.id.shimmerTask);
        shimmerFrameLayout.startShimmer();
        mTask.setVisibility(View.GONE);
        days=getIntent().getParcelableExtra("day");

        presenter=new TaskPresenter();
        rvTaskList=findViewById(R.id.rvTaskActivity);
        adapter=new TaskAdapter(this);
        rvTaskList.setAdapter(adapter);
        rvTaskList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        apiService.getAllTask(days.getDayId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Task>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Task> tasks) {
                        adapter.getItems(tasks);
                        presenter.setVisibility();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    presenter.onAttach(this);
    }

    @Override
    public void onClick(Task task) {
        presenter.onDeleteClicked(task);
    }

    @Override
    public void showList(boolean visible) {
        mTask.setVisibility(visible ? View.VISIBLE : View.GONE);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(visible ? View.GONE :View.VISIBLE);
    }

    @Override
    public void deleteTask(Task task) {
            adapter.deleteTask(task);
    }

    @Override
    public void changeActivity() {
        Intent intent=new Intent(TaskActivity.this,AddTaskActivity.class);
        intent.putExtra("days",days);
        startActivityIfNeeded(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE && data != null){
            Task task=data.getParcelableExtra("xTask");
            adapter.addTaskItems(task);
        }
    }
}