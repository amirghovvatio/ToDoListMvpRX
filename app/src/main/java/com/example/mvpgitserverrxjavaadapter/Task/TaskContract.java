package com.example.mvpgitserverrxjavaadapter.Task;

import com.example.mvpgitserverrxjavaadapter.BasePresenter;
import com.example.mvpgitserverrxjavaadapter.BaseView;
import com.example.mvpgitserverrxjavaadapter.Model.Task;

public interface TaskContract {
    interface View extends BaseView{
        void showList(boolean visible);

        void deleteTask(Task task);
        void changeActivity();
    }
    interface Presenter extends BasePresenter<View>{
        void setVisibility();

        void addTaskClicked();
        void onDeleteClicked(Task task);
    }

    interface AddTaskView extends BaseView{
        void changeActivity(int resultCode,Task task);
    }

    interface AddTaskPresenter extends BasePresenter<AddTaskView>{
        void saveChange(Task task);

    }
}
