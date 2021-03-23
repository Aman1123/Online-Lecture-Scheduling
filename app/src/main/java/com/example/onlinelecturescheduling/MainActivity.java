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
import android.widget.Toast;

import com.example.onlinelecturescheduling.AdminPanel.AdminActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText usernameED, passED;
    Button loginButton;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    String usernameString;
    String passString;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        usernameED = findViewById(R.id.userName);
        passED = findViewById(R.id.password);
        loginButton = findViewById(R.id.LoginButton);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCondition();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void checkCondition() {

        usernameString = usernameED.getText().toString().trim();
        passString = passED.getText().toString().trim();

        if (TextUtils.isEmpty(usernameString)) {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passString)) {
            Toast.makeText(getApplicationContext(), "Please Enter Pass", Toast.LENGTH_SHORT).show();
            return;
        } else {

            reference = FirebaseDatabase.getInstance().getReference().child("user_idpass").child("admin_idpass");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String uid = snapshot.child("Username").getValue().toString();
                    String pass = snapshot.child("Username").getValue().toString();
                    if (usernameString.equals(uid) && passString.equals(pass)) {
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                        finish();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}