package com.example.onlinelecturescheduling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinelecturescheduling.R;
import com.example.onlinelecturescheduling.model.ScheduledLecturesModel;

import java.util.List;

public class InstructorMainAdapter extends RecyclerView.Adapter<InstructorMainAdapter.MyViewHolder> {

    private List<ScheduledLecturesModel> ScheduledLecturesModelList;
    Context context;

    public InstructorMainAdapter(Context context, List<ScheduledLecturesModel> ScheduledLecturesModelList) {
        this.ScheduledLecturesModelList = ScheduledLecturesModelList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ins_main_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ScheduledLecturesModel scheduledLecturesModel = ScheduledLecturesModelList.get(position);

        holder.courseName.setText(scheduledLecturesModel.getCourseName());
        holder.batchDate.setText(scheduledLecturesModel.getBatchDate());
        holder.batchName.setText(scheduledLecturesModel.getBatchName());

    }

    @Override
    public int getItemCount() {
        return ScheduledLecturesModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView courseName, batchName, batchDate;
        CardView cd;

        public MyViewHolder(View view) {
            super(view);
            courseName = (TextView) view.findViewById(R.id.courseName);
            batchName = (TextView) view.findViewById(R.id.batchName);
            batchDate = (TextView) view.findViewById(R.id.batchDate);


            cd = (CardView) view.findViewById(R.id.cd1);

        }
    }

}

