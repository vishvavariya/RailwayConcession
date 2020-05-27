package com.railwayconcession.http.railwayconcession;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private TextView StudentName, StudentEmail,StudentDOB,StudentMobile,StudentGender,StudentSAPID;
    private Button EditProfile;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");



        StudentName = findViewById(R.id.StudentName);
        StudentEmail = findViewById(R.id.StudentEmail);
        StudentDOB = findViewById(R.id.StudentDOB);
        StudentSAPID = findViewById(R.id.StudentSAPID);
        StudentMobile = findViewById(R.id.StudentMobile);
        StudentGender = findViewById(R.id.StudentGender);
        EditProfile = (Button) findViewById(R.id.EditProfile);



        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        getSupportActionBar().setTitle("Profile");

        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToEditProfile();
            }
        });

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue(String.class);
                String email = dataSnapshot.child("Email").getValue(String.class);
                String SAPID = dataSnapshot.child("SAPID").getValue(String.class);
                String Mobile = dataSnapshot.child("Mobile").getValue(String.class);
                String Gender = dataSnapshot.child("Gender").getValue(String.class);
                String DOB = dataSnapshot.child("DOB").getValue(String.class);

                StudentName.setText("" +name);
                StudentEmail.setText("" +email);
                StudentMobile.setText("" +Mobile);
                StudentSAPID.setText("" +SAPID);
                StudentGender.setText("" +Gender);
                StudentDOB.setText("" +DOB);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendToEditProfile() {

        Intent editIntent = new Intent(ProfileActivity.this, EditStudentProfile.class);
        startActivity(editIntent);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

        @Override
        public void onBackPressed () {
            finish();
        }
    }
