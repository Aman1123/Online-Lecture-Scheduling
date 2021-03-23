package com.example.onlinelecturescheduling.AdminPanel;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.onlinelecturescheduling.R;
import com.example.onlinelecturescheduling.adapter.CourseAdpter;
import com.example.onlinelecturescheduling.model.CoursesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    View view;
    FloatingActionButton floatingActionButton;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;
    private CourseAdpter mAdapter;
    ProgressBar pb;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        pb = (ProgressBar) view.findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_courses);
        floatingActionButton = view.findViewById(R.id.addCoursesFab);
        floatingActionButton.setTooltipText("Add Course");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddCourses.class));
            }
        });

        Log.v("Response_INS", "Inside Couses");

        getdata();
        return view;


    }

    private void getdata() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String value = snapshot.getValue(String.class);
                if (snapshot.getChildrenCount() > 0) {
                    Log.v("Response", new Gson().toJson(snapshot.getValue()) + "\n" + snapshot.getChildrenCount());
                    List<CoursesModel> coursesModelList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        CoursesModel CoursesModel1 = (CoursesModel) dataSnapshot.getValue(CoursesModel.class);
                        coursesModelList.add(CoursesModel1);
                    }
                    mAdapter = new CourseAdpter(getActivity(), coursesModelList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    pb.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "You don't have Course", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Log.v("Response", error.getMessage());

                Toast.makeText(getActivity(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }


}