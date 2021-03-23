package com.example.onlinelecturescheduling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class InstructorLoginActivity extends AppCompatActivity {
    EditText emailED, passED;
    Button loginButton;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    String emailString;
    String passString;
TextView insHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_instructor_login);

        emailED = findViewById(R.id.insLoginEmail);
        passED = findViewById(R.id.insLoginPass);
        loginButton = findViewById(R.id.insLginButton);
        fAuth = FirebaseAuth.getInstance();




        progressBar = findViewById(R.id.pb);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCondition();
            }
        });
    }

    private void checkCondition() {
        progressBar.setVisibility(View.VISIBLE);

        emailString = emailED.getText().toString().trim();
        passString = passED.getText().toString().trim();

        if (TextUtils.isEmpty(emailString)) {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passString)) {
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passString.length() < 6) {
            Toast.makeText(getApplicationContext(), "Please Enter Correct Password ", Toast.LENGTH_SHORT).show();
            return;
        }

        fAuth.signInWithEmailAndPassword(emailString, passString).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(
                            @NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(InstructorLoginActivity.this, ins_main_activity.class);
                            startActivity(intent);
                            finish();
                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "Login failed!!",
                                    Toast.LENGTH_LONG)
                                    .show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }
}