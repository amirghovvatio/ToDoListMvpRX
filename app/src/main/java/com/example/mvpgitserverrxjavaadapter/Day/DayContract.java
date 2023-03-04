package com.example.mvpgitserverrxjavaadapter.Day;

import android.content.Context;
import android.content.Intent;

import com.example.mvpgitserverrxjavaadapter.BasePresenter;
import com.example.mvpgitserverrxjavaadapter.BaseView;
import com.example.mvpgitserverrxjavaadapter.Model.Days;

public interface DayContract {
    interface GetView extends BaseView{
        void showList(boolean rvVisibility,boolean shimmerVisibility);
        void deleteDays(Days days);

        void changeActivity(int requestCode);

        void goToTaskActivity(int requestCode,Days days);

    }
    interface GetPresenter extends BasePresenter<GetView>{
        void setVisibility();
        void onDeleteDayClicked(Days days);

        void  addDay();

        void taskActivity(Days days);
    }

    interface AddDayView extends BaseView{
        void setActivityVisible(int resultCode,Days days,boolean visible);
    }

    interface AddDayPresenter extends BasePresenter<AddDayView>{
        void saveChanges(Days days);
    }
}
