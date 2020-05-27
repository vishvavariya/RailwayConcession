package com.railwayconcession.http.railwayconcession;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApplicationStatus extends AppCompatActivity {
    private TextView StudentName, StudentEmail,StudentMobile,StudentRemarks,StudentSAPID;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_status);
        getSupportActionBar().setTitle("Application Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final String sapid = getIntent().getStringExtra("sapid");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        StudentName = findViewById(R.id.StudentName);
        StudentEmail = findViewById(R.id.StudentEmail);
        StudentSAPID = findViewById(R.id.StudentSAPID);
        StudentMobile = findViewById(R.id.StudentMobile);
         StudentRemarks = findViewById(R.id.StudentRemarks);


        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Status").child(mAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue(String.class);
                String email = dataSnapshot.child("Email").getValue(String.class);
                String SAPID = dataSnapshot.child("SAPID").getValue(String.class);
                String Mobile = dataSnapshot.child("Mobile").getValue(String.class);
               String Remarks = dataSnapshot.child("Remarks").getValue(String.class);

                StudentName.setText("" +name);
                StudentEmail.setText("" +email);
                StudentMobile.setText("" +Mobile);
                StudentSAPID.setText("" +SAPID);
                StudentRemarks.setText("" +Remarks);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ApplicationStatus.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), RailwayConcessionActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
