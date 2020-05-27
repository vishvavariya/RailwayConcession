package com.railwayconcession.http.railwayconcession;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class StudentDetails extends AppCompatActivity {

    private TextView StudentCoach;
    private TextView StudentSuburb;
    private TextView StudentName;
    private TextView StudentEmail;
    private TextView StudentSAPID;
    private TextView StudentDOB;
    private TextView StudentFrom;
    private TextView StudentTo;
    private TextView StudentGender;
    private TextView StudentPeriod;
    private TextView StudentMobile;
    private TextView StudentUID;
    private Button BtnConfirm;
    private Button BtnReject;
    private EditText StudentRemarks;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private Firebase mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        StudentName = ( TextView ) findViewById(R.id.StudentName);
        StudentEmail = ( TextView ) findViewById(R.id.StudentEmail);
        StudentMobile = ( TextView ) findViewById(R.id.StudentMobile);
        StudentSAPID = ( TextView ) findViewById(R.id.StudentSAPID);
        StudentDOB = ( TextView ) findViewById(R.id.StudentDOB);
        StudentFrom = ( TextView ) findViewById(R.id.StudentFrom);
        StudentTo = ( TextView ) findViewById(R.id.StudentTo);
        StudentGender = ( TextView ) findViewById(R.id.StudentGender);
        StudentPeriod = ( TextView ) findViewById(R.id.StudentPeriod);
        StudentSuburb = ( TextView ) findViewById(R.id.StudentSuburb);
        StudentCoach = ( TextView ) findViewById(R.id.StudentCoach);
        StudentUID = ( TextView ) findViewById(R.id.StudentUID);
        BtnConfirm = ( Button ) findViewById(R.id.BtnConfirm);
        BtnReject = ( Button ) findViewById(R.id.BtnReject);
        final String name = getIntent().getStringExtra("name");
        final String email = getIntent().getStringExtra("email");
        final String mobile = getIntent().getStringExtra("mobile");
        final String sapid = getIntent().getStringExtra("sapid");
        final String dob = getIntent().getStringExtra("dob");
        final String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        final String gender = getIntent().getStringExtra("gender");
        final String period = getIntent().getStringExtra("period");
        final String suburb = getIntent().getStringExtra("suburb");
        final String Coach = getIntent().getStringExtra("Coach");
        final String uid = getIntent().getStringExtra("uid");
        StudentName.setText(name);
        StudentSAPID.setText(sapid);
        StudentEmail.setText(email);
        StudentMobile.setText(mobile);
        StudentDOB.setText(dob);
        StudentTo.setText("Vile Parle");
        StudentFrom.setText(from);
        StudentGender.setText(gender);
        StudentPeriod.setText(period);
        StudentSuburb.setText(suburb);
        StudentCoach.setText(Coach);

        BtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentRemarks = ( EditText ) findViewById(R.id.StudentRemarks);
                final String remarks = StudentRemarks.getText().toString().trim();

                final DatabaseReference batchReference = FirebaseDatabase.getInstance().getReference().child("Status").child(uid);
                batchReference.child("Name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // batchReference.child("Name").setValue(name);
                            batchReference.child("SAPID").setValue(sapid);
                            batchReference.child("Mobile").setValue(mobile);
                            batchReference.child("Email").setValue(email);
                            batchReference.child("DOB").setValue(dob);
                            batchReference.child("Gender").setValue(gender);
                            batchReference.child("From").setValue(from);
                            batchReference.child("Suburb").setValue(suburb);
                            batchReference.child("Coach").setValue(Coach);
                            batchReference.child("Period").setValue(period);
                            batchReference.child("Remarks").setValue(remarks);


                            if (task.isSuccessful()) {
                                Toast.makeText(StudentDetails.this, "You confirmed an application", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference().child("Requests").child(uid).removeValue();
                                Intent dIntent = new Intent(StudentDetails.this, AdminDashboard.class);
                                startActivity(dIntent);


                            }

                        }

                    }
                });
            }
        });

        BtnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentRemarks = ( EditText ) findViewById(R.id.StudentRemarks);
                final String remarks = StudentRemarks.getText().toString().trim();

                final DatabaseReference batchReference = FirebaseDatabase.getInstance().getReference().child("Status").child(uid);
                batchReference.child("Name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // batchReference.child("Name").setValue(name);
                            batchReference.child("SAPID").setValue(sapid);
                            batchReference.child("Mobile").setValue(mobile);
                            batchReference.child("Email").setValue(email);
                            batchReference.child("DOB").setValue(dob);
                            batchReference.child("Gender").setValue(gender);
                            batchReference.child("From").setValue(from);
                            batchReference.child("Suburb").setValue(suburb);
                            batchReference.child("Coach").setValue(Coach);
                            batchReference.child("Period").setValue(period);
                            batchReference.child("Remarks").setValue("REJECTED");


                            if (task.isSuccessful()) {
                                Toast.makeText(StudentDetails.this, "You rejected an application", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference().child("Requests").child(uid).removeValue();
                                Intent dIntent = new Intent(StudentDetails.this, AdminDashboard.class);
                                startActivity(dIntent);


                            }

                        }

                    }
                });
            }
        });
    }}

