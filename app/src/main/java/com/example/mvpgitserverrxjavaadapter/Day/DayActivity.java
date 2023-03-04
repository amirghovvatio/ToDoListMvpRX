package com.example.mvpgitserverrxjavaadapter.Day;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mvpgitserverrxjavaadapter.Model.Days;
import com.example.mvpgitserverrxjavaadapter.Model.MainApiService;
import com.example.mvpgitserverrxjavaadapter.R;
import com.example.mvpgitserverrxjavaadapter.Task.TaskActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DayActivity extends AppCompatActivity implements DayContract.GetView,DayAdapter.DayEventListener {
    private DayAdapter adapter;
    private RecyclerView rvDayList;
    private DayContract.GetPresenter presenter;
    private MainApiService apiService;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout mainListFrame;
    private Disposable disposable;
    private View addDayBtn;
    public static final int REQUEST_CODE=2010;
    public static final int RESULT_CODE_ADD=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvDayList=findViewById(R.id.rvDayList);
        addDayBtn=findViewById(R.id.addDayBtn);
        addDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addDay();
//                presenter.addDay(MainActivity.this,AddDayActivity.class);
//                Intent intent=new Intent(MainActivity.this,AddDayActivity.class);
//                startActivityIfNeeded(intent,2010);

            }
        });
        shimmerFrameLayout=findViewById(R.id.shimmerDay);
        mainListFrame=findViewById(R.id.mainLinearLayout);
        presenter=new DayPresenter();
        shimmerFrameLayout.startShimmer();

        adapter=new DayAdapter(this);
        rvDayList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rvDayList.setAdapter(adapter);
        apiService=new MainApiService();
        apiService.getAllDays().
                delaySubscription(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Days>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable=d;
                    }

                    @Override
                    public void onSuccess(List<Days> days) {
                        adapter.getItems(days);
                        presenter.setVisibility();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(DayActivity.this, "We Have An Error While Getting List", Toast.LENGTH_SHORT).show();
                    }
                });

        presenter.onAttach(this);
    }

    @Override
    public void showList(boolean rvVisibility, boolean shimmerVisibility) {
        shimmerFrameLayout.setVisibility(shimmerVisibility ? View.VISIBLE : View.GONE);
        mainListFrame.setVisibility(rvVisibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void deleteDays(Days days) {
        adapter.deleteItem(days);
    }

    @Override
    public void changeActivity(int requestCode) {
        Intent intent=new Intent(DayActivity.this,AddDayActivity.class);
        startActivityIfNeeded(intent,requestCode);
    }

    @Override
    public void goToTaskActivity(int requestCode,Days days) {
        Intent intent=new Intent(DayActivity.this, TaskActivity.class);
        intent.putExtra("day",days);
        startActivity(intent);
    }


    @Override
    public void onClick(Days days) {
        apiService.deleteDay(days.getDayId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if (aBoolean){
                            presenter.onDeleteDayClicked(days);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void onLongClicked(Days days) {
        presenter.taskActivity(days);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE_ADD && data != null){
            Days days=data.getParcelableExtra("xtra");
            adapter.addTaskItems(days);
        }
    }
}