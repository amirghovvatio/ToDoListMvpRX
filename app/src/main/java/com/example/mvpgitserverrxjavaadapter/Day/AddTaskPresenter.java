package com.example.mvpgitserverrxjavaadapter.Day;

import com.example.mvpgitserverrxjavaadapter.Model.Task;
import com.example.mvpgitserverrxjavaadapter.Task.AddTaskActivity;
import com.example.mvpgitserverrxjavaadapter.Task.TaskActivity;
import com.example.mvpgitserverrxjavaadapter.Task.TaskContract;

public class AddTaskPresenter implements TaskContract.AddTaskPresenter {
    private TaskContract.AddTaskView view;
    @Override
    public void onAttach(TaskContract.AddTaskView view) {
        this.view=view;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void saveChange(Task task) {
        this.view.changeActivity(TaskActivity.RESULT_CODE,task);
    }
}
