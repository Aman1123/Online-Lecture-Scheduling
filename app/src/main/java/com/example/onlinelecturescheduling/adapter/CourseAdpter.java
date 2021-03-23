package com.example.onlinelecturescheduling.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinelecturescheduling.R;
import com.example.onlinelecturescheduling.ScheduleLecture;
import com.example.onlinelecturescheduling.model.CoursesModel;
import com.example.onlinelecturescheduling.model.InstrutorModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CourseAdpter extends RecyclerView.Adapter<CourseAdpter.MyViewHolder> {

    private List<CoursesModel> coursesModelList;
    Context context;
    SharedPreferences sharedpreferences;


    public CourseAdpter(Context context, List<CoursesModel> coursesModelList) {
        this.coursesModelList = coursesModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CoursesModel coursesModel = coursesModelList.get(position);

        holder.courseName.setText(coursesModel.getCourseName());
        holder.couseDes.setText(coursesModel.getCourseDescription());
        Picasso.get()
                .load(coursesModel.getCourseImg())
                .fit()
                .centerCrop()
                .into(holder.imageView);
        String courseName = coursesModel.getCourseName();


        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ScheduleLecture.class);
                i.putExtra("course", coursesModel);
                context.startActivity(i);

                sharedpreferences = context.getSharedPreferences("Mysharedpreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("courseName", courseName);
                editor.putString("courseId", coursesModel.getCourseId());

                editor.commit();


            }
        });

    }


    @Override
    public int getItemCount() {
        return coursesModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView courseName, couseDes;
        ImageView imageView;

        CardView cd;

        public MyViewHolder(View view) {
            super(view);
            courseName = (TextView) view.findViewById(R.id.courseName);
            couseDes = (TextView) view.findViewById(R.id.courseDes);
            imageView = (ImageView) view.findViewById(R.id.course_img);
            cd = (CardView) view.findViewById(R.id.course_cd);
        }
    }

}
