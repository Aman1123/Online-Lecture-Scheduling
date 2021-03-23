package com.example.onlinelecturescheduling.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinelecturescheduling.MainActivity;
import com.example.onlinelecturescheduling.R;
import com.example.onlinelecturescheduling.ScheduleLecture;
import com.example.onlinelecturescheduling.model.InstrutorModel;

import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.MyViewHolder> {

    private List<InstrutorModel> instrutorModelList;
    Context context;

    public InstructorAdapter(Context context, List<InstrutorModel> instrutorModelList) {
        this.instrutorModelList = instrutorModelList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InstrutorModel instrutorModel = instrutorModelList.get(position);
        int count = position + 1;
        holder.instructorNName.setText(count + ". " + instrutorModel.getInstructorName());
    }

    @Override
    public int getItemCount() {
        return instrutorModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView instructorNName;
        CardView cd;

        public MyViewHolder(View view) {
            super(view);
            instructorNName = (TextView) view.findViewById(R.id.instructorName);
            cd = (CardView) view.findViewById(R.id.cd);

        }
    }

}
