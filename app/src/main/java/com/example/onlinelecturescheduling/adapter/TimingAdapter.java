package com.example.onlinelecturescheduling.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.onlinelecturescheduling.InstructorActivity;
import com.example.onlinelecturescheduling.R;
import com.example.onlinelecturescheduling.model.BatchDateModel;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TimingAdapter extends RecyclerView.Adapter<TimingAdapter.MyViewHolder> {

    private List<BatchDateModel> batchDateModelList;
    Context context;
    SharedPreferences sharedpreferences;


    public TimingAdapter(Context context, List<BatchDateModel> BatchDateModelList) {
        this.batchDateModelList = BatchDateModelList;
        this.context = context;
    }

    @Override
    public TimingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_timing_adapter, parent, false);

        return new TimingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BatchDateModel batchDateModel = batchDateModelList.get(position);
        holder.batchName.setText(batchDateModel.getBatchName());
        holder.batchDate.setText(batchDateModel.getBatchDate());


        String batchname = batchDateModel.getBatchName();
        String batchDate = batchDateModel.getBatchDate();

        holder.cd.setOnClickListener(v -> {
            Intent intent = new Intent(context, InstructorActivity.class);
            context.startActivity(intent);


            sharedpreferences = context.getSharedPreferences("Mysharedpreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("batchName", batchname);
            editor.putString("batchDate", batchDate);

            editor.commit();

        });

    }

    @Override
    public int getItemCount() {
        return batchDateModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView batchName, batchDate;
        CardView cd;

        public MyViewHolder(View view) {
            super(view);
            batchName = (TextView) view.findViewById(R.id.batchName1);
            batchDate = (TextView) view.findViewById(R.id.batchDate1);
            cd = (CardView) view.findViewById(R.id.cd1);

        }
    }

}
