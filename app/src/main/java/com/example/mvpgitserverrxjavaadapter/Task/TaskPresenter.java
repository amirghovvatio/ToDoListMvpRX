package com.example.mvpgitserverrxjavaadapter.Task;

import com.example.mvpgitserverrxjavaadapter.Model.Task;

public class TaskPresenter implements TaskContract.Presenter{
    private TaskContract.View view;
    @Override
    public void onAttach(TaskContract.View view) {
        this.view=view;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void setVisibility() {
        this.view.showList(true);
    }

    @Override
    public void addTaskClicked() {
        view.changeActivity();
    }

    @Override
    public void onDeleteClicked(Task task) {
        this.view.deleteTask(task);
    }
}
