package com.example.onlinelecturescheduling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.onlinelecturescheduling.adapter.InstructorMainAdapter;
import com.example.onlinelecturescheduling.model.ScheduledLecturesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ins_main_activity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;
    private InstructorMainAdapter mAdapter;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_main_activity);
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.rv_instructor);
        getdata();


    }

    private void getdata() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ScheduledLectures").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {
                    List<ScheduledLecturesModel> ScheduledLecturesModelList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ScheduledLecturesModel ScheduledLecturesModel1 = (ScheduledLecturesModel) dataSnapshot.getValue(ScheduledLecturesModel.class);
                        ScheduledLecturesModelList.add(ScheduledLecturesModel1);
                    }
                    mAdapter = new InstructorMainAdapter(getApplicationContext(), ScheduledLecturesModelList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    pb.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getApplicationContext(), "You don't have Instructor", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("Response", error.getMessage());
            }
        });
    }
}
