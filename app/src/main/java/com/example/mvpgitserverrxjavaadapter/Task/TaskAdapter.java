package com.example.mvpgitserverrxjavaadapter.Task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpgitserverrxjavaadapter.Model.Task;
import com.example.mvpgitserverrxjavaadapter.R;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks=new ArrayList<>();
    private TaskEventListener eventListener;

    public TaskAdapter(TaskEventListener eventListener){
        this.eventListener = eventListener;
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list,parent,false);
        return new TaskViewHolder(view);
    }
    public void getItems(List<Task> tasks){
        this.tasks=tasks;
        notifyDataSetChanged();
    }
    public void deleteTask(Task task){
        int index=this.tasks.indexOf(task);
        this.tasks.remove(index);
        notifyItemRemoved(index);
    }

    public void addTaskItems(Task task){
        this.tasks.add(0,task);
        notifyItemInserted(0);
    }
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindTasks(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private CheckBox chkTask;
        private ImageView imgDelete;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            chkTask=itemView.findViewById(R.id.checkBoxTaskList);
            imgDelete=itemView.findViewById(R.id.deleteTaskList);
        }
        public void bindTasks(Task task){
            chkTask.setText(task.getTitle());
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.onClick(task);
                }
            });
        }
    }
    public interface TaskEventListener{
        void onClick(Task task);
    }
}
