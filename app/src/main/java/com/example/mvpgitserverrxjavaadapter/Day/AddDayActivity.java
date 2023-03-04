package com.example.mvpgitserverrxjavaadapter.Day;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mvpgitserverrxjavaadapter.Model.Days;
import com.example.mvpgitserverrxjavaadapter.Model.MainApiService;
import com.example.mvpgitserverrxjavaadapter.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddDayActivity extends AppCompatActivity implements DayContract.AddDayView {
    private static final String TAG = "AddDayActivity";
    private TextInputEditText edtDayName;
    private TextInputEditText edtDate;
    private MainApiService apiService;
    private DayContract.AddDayPresenter presenter;
    private ExtendedFloatingActionButton saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_day);
        saveBtn=findViewById(R.id.saveDayAddDayActivity);
        
        presenter=new AddDayPresenter();
        
        edtDayName=findViewById(R.id.edtDayAddDay);
        edtDate=findViewById(R.id.edtDateAddDay);
        apiService=new MainApiService();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtDayName.getText().length() > 4 &&
                        edtDate.getText().length() > 5) {
                    apiService.addDays(edtDayName.getText().toString(), edtDate.getText().toString())
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<Days>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(Days days) {
                                    Log.i(TAG, "onSuccess: ");
                                    presenter.saveChanges(days);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                }else {
                    Toast.makeText(AddDayActivity.this, "Please Complete The Field", Toast.LENGTH_SHORT).show();
                }
            }

        });

        
        
        
        
        
        
        
        presenter.onAttach(this);
    }


    @Override
    public void setActivityVisible(int resultCode, Days days, boolean visible) {
        Intent intent=new Intent();
        intent.putExtra("xtra",days);
        setResult(resultCode,intent);
        finish();
    }
}