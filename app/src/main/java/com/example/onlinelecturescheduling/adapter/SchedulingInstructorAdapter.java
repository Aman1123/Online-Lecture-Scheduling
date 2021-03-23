package com.example.onlinelecturescheduling.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinelecturescheduling.R;
import com.example.onlinelecturescheduling.ScheduleLecture;
import com.example.onlinelecturescheduling.model.CoursesModel;
import com.example.onlinelecturescheduling.model.InstrutorModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SchedulingInstructorAdapter extends RecyclerView.Adapter<SchedulingInstructorAdapter.MyViewHolder> {

    private List<InstrutorModel> instrutorModelList;
    List<CoursesModel> coursesModelList;
    Context context;
    AlertDialog.Builder builder;


    public SchedulingInstructorAdapter(Context context, List<InstrutorModel> instrutorModelList, List<CoursesModel> coursesModelList) {
        this.instrutorModelList = instrutorModelList;
        this.coursesModelList = coursesModelList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor_new_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InstrutorModel instrutorModel = instrutorModelList.get(position);
        int count = position + 1;
        holder.instructorNName.setText(count + ". " + instrutorModel.getInstructorName());

        String instructorNName = instrutorModel.getInstructorName();
        String instructorId = instrutorModel.getInstructorID();


        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("Mysharedpreferences", MODE_PRIVATE);
                String batchDateQ = sharedPreferences.getString("batchDate", "");
                String batchNameQ = sharedPreferences.getString("batchName", "");
                String courseNameQ = sharedPreferences.getString("courseName", "");

                String checkingCondn = courseNameQ + "_" + batchNameQ + "_" + batchDateQ;

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("ScheduledLectures").child(instructorId);
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(checkingCondn)) {
                            Toast.makeText(context, "Instructor Already Assigned.", Toast.LENGTH_SHORT).show();
                        } else {
                            builder = new AlertDialog.Builder(context);
                            builder.setMessage("Do you want to Schedule this course ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //dialog.dismiss();

                                            SharedPreferences sharedPreferences = context.getSharedPreferences("Mysharedpreferences", MODE_PRIVATE);
                                            String batchName = sharedPreferences.getString("batchName", "");
                                            String batchDate = sharedPreferences.getString("batchDate", "");
                                            String courseName = sharedPreferences.getString("courseName", "");
                                            String instructorID = instrutorModel.getInstructorID();

                                            DatabaseReference databaseReference;
                                            FirebaseDatabase firebaseDatabase;
                                            firebaseDatabase = FirebaseDatabase.getInstance();
                                            databaseReference = firebaseDatabase.getReference("ScheduledLectures");

                                            HashMap<String, Object> HM = new HashMap<String, Object>();
                                            HM.put("batchName", batchName);
                                            HM.put("batchDate", batchDate);
                                            HM.put("courseName", courseName);

                                            databaseReference.child(instructorID).child(courseName + "_" + batchName + "_" + batchDate).setValue(HM)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Intent i = new Intent(context, ScheduleLecture.class);
                                                            context.startActivity(i);
                                                            ((Activity) context).finish();

                                                            Toast.makeText(context, "Scheduled!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();
                                            Toast.makeText(context, "No action",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.setTitle("Select Yes or No");
                            alert.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
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
