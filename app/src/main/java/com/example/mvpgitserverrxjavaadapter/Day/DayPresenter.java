package com.example.mvpgitserverrxjavaadapter.Day;

import com.example.mvpgitserverrxjavaadapter.Model.Days;

public class DayPresenter implements DayContract.GetPresenter{
    private DayContract.GetView view;
    @Override
    public void onAttach(DayContract.GetView view) {
        this.view=view;
    }

    @Override
    public void onDetach() {
        this.view=null;
    }

    @Override
    public void setVisibility() {
        view.showList(true,false);
    }

    @Override
    public void onDeleteDayClicked(Days days) {
        this.view.deleteDays(days);
    }

    @Override
    public void addDay() {
        view.changeActivity(DayActivity.REQUEST_CODE);
    }

    @Override
    public void taskActivity(Days days) {
        view.goToTaskActivity(DayActivity.REQUEST_CODE,days);
    }
}
