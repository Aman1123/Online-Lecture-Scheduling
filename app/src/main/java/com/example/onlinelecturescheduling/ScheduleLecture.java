package com.example.onlinelecturescheduling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.onlinelecturescheduling.adapter.TimingAdapter;
import com.example.onlinelecturescheduling.model.BatchDateModel;
import com.example.onlinelecturescheduling.model.CoursesModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ScheduleLecture extends AppCompatActivity {

    CoursesModel coursesModel;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String courseId;

    Button b1;
    private RecyclerView recyclerView;
    private TimingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_lecture);
        getSupportActionBar().setTitle("Showing Batch");

        b1 = findViewById(R.id.addTiming);
        recyclerView = (RecyclerView) findViewById(R.id.timing_rv);


        Log.v("Response_ID", "" + courseId);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddingBatches.class);
                i.putExtra("course", coursesModel);
                startActivity(i);
            }
        });
        getdata();
    }

    private void getdata() {
        SharedPreferences sharedPreferences = getSharedPreferences("Mysharedpreferences", MODE_PRIVATE);
        String courseId = sharedPreferences.getString("courseId", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses").child(courseId).child("Batches");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.v("Response_batch", new Gson().toJson(snapshot.getValue()) + "\n" + snapshot.getChildrenCount());
                if (snapshot.getChildrenCount() > 0) {

                    List<BatchDateModel> batchModelList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        BatchDateModel batchDateModel = (BatchDateModel) dataSnapshot.getValue(BatchDateModel.class);
                        batchModelList.add(batchDateModel);
                    }
                    Log.v("Response_batch", new Gson().toJson(batchModelList));

                    mAdapter = new TimingAdapter(getApplicationContext(), batchModelList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }else{
                    Toast.makeText(getApplicationContext(), "You don't have Batch.", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("Response", error.getMessage());
                Toast.makeText(getApplicationContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}