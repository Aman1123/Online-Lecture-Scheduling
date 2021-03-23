package com.example.onlinelecturescheduling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlinelecturescheduling.adapter.InstructorAdapter;
import com.example.onlinelecturescheduling.model.CoursesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class AddingBatches extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    CoursesModel coursesModel;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String date = null;
    EditText getName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_batches);
        getSupportActionBar().setTitle("Add Multiple Batch");

        getName = findViewById(R.id.batchName);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        date = "" + i + "-" + i1 + "-" + i2;
        uploadData();
    }

    private void uploadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Mysharedpreferences", MODE_PRIVATE);
        String courseId = sharedPreferences.getString("courseId", "");

        String getBatchName = getName.getText().toString().trim();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");

        HashMap<String, Object> HM = new HashMap<String, Object>();
        HM.put("batchName", getBatchName);
        HM.put("batchDate", date);

        databaseReference.child(courseId).child("Batches").child(getBatchName).setValue(HM)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Date Saved", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), ScheduleLecture.class);
                        i.putExtra("course", coursesModel);
                        startActivity(i);
                        finish();
                    }
                });
    }
}