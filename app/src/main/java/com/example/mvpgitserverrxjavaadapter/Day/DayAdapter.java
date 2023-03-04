package com.example.mvpgitserverrxjavaadapter.Day;

import android.hardware.lights.LightState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpgitserverrxjavaadapter.Model.Days;
import com.example.mvpgitserverrxjavaadapter.Model.Task;
import com.example.mvpgitserverrxjavaadapter.R;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    private List<Days> daysList=new ArrayList<>();
    private DayEventListener eventListener;

    public DayAdapter(DayEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.day_list,parent,false);
        return new DayViewHolder(view);
    }
    public void getItems(List<Days> days){
        this.daysList=days;
        notifyDataSetChanged();
    }
    public void deleteItem(Days days){
        int index=this.daysList.indexOf(days);
        this.daysList.remove(index);
        notifyItemRemoved(index);
    }
    public void addTaskItems(Days day){
        this.daysList.add(0,day);
        notifyItemInserted(0);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.bindDays(daysList.get(position));
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        private CheckBox chkDayName;
        private TextView tvDate;
        private ImageView imgDelete;
        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            chkDayName=itemView.findViewById(R.id.chekcBoxDayList);
            tvDate=itemView.findViewById(R.id.tvDateDayList);
            imgDelete=itemView.findViewById(R.id.deleteDayList);
        }
        public void bindDays(Days days){
            chkDayName.setText(days.getDayName());
            tvDate.setText(days.getDate());
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.onClick(days);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    eventListener.onLongClicked(days);
                    return false;
                }
            });
        }
    }
    public interface DayEventListener{
        void onClick(Days days);
        void onLongClicked(Days days);
    }
}
