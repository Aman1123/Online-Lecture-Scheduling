package com.example.onlinelecturescheduling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinelecturescheduling.AdminPanel.AdminActivity;
import com.example.onlinelecturescheduling.AdminPanel.Instructor;
import com.example.onlinelecturescheduling.model.InstrutorModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {
    EditText regNameED, regPassED, regPassED2, regEmailED;
    TextView loginHere;
    Button signUpButton;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    String emailText, usernameText;
    String passText;
    String passText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        regNameED = findViewById(R.id.fullName);
        regPassED = findViewById(R.id.userPass1);
        regEmailED = findViewById(R.id.userEmail);
        progressBar = findViewById(R.id.pb);
        regPassED2 = findViewById(R.id.userPass2);

        signUpButton = findViewById(R.id.signUpButton);

        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("instructor_idpass");
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCondition();
                progressBar.setVisibility(View.VISIBLE);

            }
        });
    }

    private void checkCondition() {
        usernameText = regNameED.getText().toString().trim();
        emailText = regEmailED.getText().toString().trim();
        passText = regPassED.getText().toString().trim();
        passText2 = regPassED2.getText().toString().trim();

        if (TextUtils.isEmpty(emailText)) {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passText)) {
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passText2)) {
            Toast.makeText(getApplicationContext(), "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passText.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password Too Short", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passText.equals(passText2)) {

            fAuth.createUserWithEmailAndPassword(emailText, passText)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.v("Response", new Gson().toJson(task));
                            if (task.isSuccessful()) {
                                InstrutorModel instrutorModel = new InstrutorModel(usernameText, emailText, passText, fAuth.getCurrentUser().getUid());
                                reference.child(fAuth.getCurrentUser().getUid()).setValue(instrutorModel)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                                                Toast.makeText(getApplicationContext(), "Record saved", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                                finish();
                                            }
                                        });
                                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "SignUp failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

}