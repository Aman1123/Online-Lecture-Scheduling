package com.example.onlinelecturescheduling.AdminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.onlinelecturescheduling.MainActivity;
import com.example.onlinelecturescheduling.R;
import com.example.onlinelecturescheduling.model.CoursesModel;
import com.example.onlinelecturescheduling.model.InstrutorModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.UUID;

public class AddCourses extends AppCompatActivity {
    RadioButton mradioButton;
    RadioGroup radioGroup;

    private Button btnChoose, btnUpload, submitButton;
    private ImageView imageView;
    private Uri filePath;
    EditText courseName, courseDes;
    private final int PICK_IMAGE_REQUEST = 10;

    FirebaseStorage storage;
    StorageReference storageReference;
    StorageTask task;
    DatabaseReference reference;

    String courseNameText, courseDesText, courseIdText, courseLvl, courseImgUrl, urlGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        getSupportActionBar().setTitle("Add Course");


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnChoose = (Button) findViewById(R.id.uploadImg);
        btnUpload = (Button) findViewById(R.id.submitCourse);
        imageView = (ImageView) findViewById(R.id.courseImg);
        submitButton = (Button) findViewById(R.id.submitCourse);
        courseName = findViewById(R.id.courseName);
        courseDes = findViewById(R.id.courseDes);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        reference = FirebaseDatabase.getInstance().getReference("Courses");

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (task != null && task.isInProgress()) {
                    Toast.makeText(AddCourses.this, "In progress ", Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage();
                }
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            urlGlobal = "images/" + UUID.randomUUID();
            StorageReference ref = storageReference.child(urlGlobal);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    courseImgUrl = uri.toString();
                                    Log.v("Response_uri", new Gson().toJson(uri) + "\n" + uri.getPath() + "," + courseImgUrl);
                                    uploadCourseToFirebase();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddCourses.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }


    private void uploadCourseToFirebase() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        mradioButton = findViewById(radioId);

        courseLvl = mradioButton.getText().toString().trim();
        courseNameText = courseName.getText().toString().trim();
        courseDesText = courseDes.getText().toString().trim();
        courseIdText = courseNameText;


        CoursesModel coursesModel = new CoursesModel(courseIdText, courseNameText, courseDesText, courseImgUrl, courseLvl);
        reference.child(courseIdText).setValue(coursesModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(i);
                        finish();
                        Toast.makeText(getApplicationContext(), "Record saved", Toast.LENGTH_SHORT).show();
                    }
                });


    }

}
