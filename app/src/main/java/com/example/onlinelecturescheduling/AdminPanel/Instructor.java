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
import android.widget.Toast;

import com.example.onlinelecturescheduling.R;
import com.example.onlinelecturescheduling.RegisterActivity;
import com.example.onlinelecturescheduling.adapter.InstructorAdapter;
import com.example.onlinelecturescheduling.model.InstrutorModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class Instructor extends Fragment {

    View view;
    FloatingActionButton floatingActionButton;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;
    private InstructorAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_instructor, container, false);
        floatingActionButton = view.findViewById(R.id.addInstructor);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_instructor);
        floatingActionButton.setTooltipText("Add Instructor");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });
        Log.v("Response_INS", "Inside instructor");

        getdata();
        return view;
    }

    private void getdata() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("instructor_idpass");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getChildrenCount() > 0) {
                    Log.v("Response_INS", new Gson().toJson(snapshot.getValue()) + "\n" + snapshot.getChildrenCount());
                    List<InstrutorModel> instrutorModelList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        InstrutorModel instrutorModel1 = (InstrutorModel) dataSnapshot.getValue(InstrutorModel.class);
                        instrutorModelList.add(instrutorModel1);
                    }
                    Log.v("Response_INS", new Gson().toJson(instrutorModelList));

                    mAdapter = new InstructorAdapter(getActivity(), instrutorModelList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                } else {
                    Toast.makeText(getActivity(), "You don't have Instructor", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.v("Response", error.getMessage());

                Toast.makeText(getActivity(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}