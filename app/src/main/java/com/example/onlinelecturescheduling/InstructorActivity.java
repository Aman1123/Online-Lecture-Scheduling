package com.example.onlinelecturescheduling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.onlinelecturescheduling.adapter.SchedulingInstructorAdapter;
import com.example.onlinelecturescheduling.model.BatchDateModel;
import com.example.onlinelecturescheduling.model.CoursesModel;
import com.example.onlinelecturescheduling.model.InstrutorModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class InstructorActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;
    private SchedulingInstructorAdapter mAdapter;

    BatchDateModel batchDateModel;
    List<CoursesModel> coursesModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        getSupportActionBar().setTitle("Select Instructor to Assign Batch");

        recyclerView = (RecyclerView) findViewById(R.id.rv_instructor);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            batchDateModel = (BatchDateModel) getIntent().getSerializableExtra("course");
        }

        getdata();


    }

    private void getdata() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("instructor_idpass");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String value = snapshot.getValue(String.class);
                Log.v("Response_INS", new Gson().toJson(snapshot.getValue()) + "\n" + snapshot.getChildrenCount());
                if (snapshot.getChildrenCount() > 0) {
                    List<InstrutorModel> instrutorModelList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        InstrutorModel instrutorModel1 = (InstrutorModel) dataSnapshot.getValue(InstrutorModel.class);
                        instrutorModelList.add(instrutorModel1);
                    }

                    Log.v("Response_INS", new Gson().toJson(instrutorModelList));

                    mAdapter = new SchedulingInstructorAdapter(InstructorActivity.this, instrutorModelList, coursesModelList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(InstructorActivity.this);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have Instructor", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("Response", error.getMessage());
                Toast.makeText(InstructorActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}