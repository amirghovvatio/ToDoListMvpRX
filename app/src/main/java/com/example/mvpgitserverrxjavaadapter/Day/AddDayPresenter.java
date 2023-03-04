package com.example.mvpgitserverrxjavaadapter.Day;

import com.example.mvpgitserverrxjavaadapter.Model.Days;

public class AddDayPresenter implements DayContract.AddDayPresenter{
    private DayContract.AddDayView view;
    @Override
    public void onAttach(DayContract.AddDayView view) {
        this.view=view;
    }

    @Override
    public void onDetach() {
        this.view=null;
    }

    @Override
    public void saveChanges(Days days) {
        this.view.setActivityVisible(DayActivity.RESULT_CODE_ADD,days,false);
     }
}
