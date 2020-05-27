package com.railwayconcession.http.railwayconcession;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditStudentProfile extends AppCompatActivity {
    private TextView StudentName, StudentEmail,StudentDOB,StudentMobile,StudentSAPID;
    private Button btnUpdateProfile;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Edit Profile");

        StudentName = findViewById(R.id.StudentName);
        StudentDOB = findViewById(R.id.StudentDOB);
       // StudentSAPID = findViewById(R.id.StudentSAPID);
        StudentMobile = findViewById(R.id.StudentMobile);
        btnUpdateProfile = ( Button ) findViewById(R.id.btnUpdateProfile);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue(String.class);
              //  String SAPID = dataSnapshot.child("SAPID").getValue(String.class);
                String Mobile = dataSnapshot.child("Mobile").getValue(String.class);
                String DOB = dataSnapshot.child("DOB").getValue(String.class);

                StudentName.setText("" + name);
                StudentMobile.setText("" + Mobile);
               // StudentSAPID.setText("" + SAPID);
                StudentDOB.setText("" + DOB);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditStudentProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String Name = StudentName.getText().toString().trim();
                final String Mobile = StudentMobile.getText().toString().trim();
               // final String SAPID = StudentSAPID.getText().toString().trim();
                final String DOB = StudentDOB.getText().toString().trim();

                final DatabaseReference batchReference = firebaseDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid());

                databaseReference.child("Name").setValue(Name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            batchReference.child("Name").setValue(Name);
                            batchReference.child("Mobile").setValue(Mobile);
                           // batchReference.child("SAPID").setValue(SAPID);
                            batchReference.child("DOB").setValue(DOB);

                            databaseReference.child("Name").setValue(Name);
                            databaseReference.child("Mobile").setValue(Mobile);
                           // databaseReference.child("SAPID").setValue(SAPID);
                            databaseReference.child("DOB").setValue(DOB);


                                    if (task.isSuccessful()) {
                                        Toast.makeText(EditStudentProfile.this, R.string.profile_updated_success, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(EditStudentProfile.this, ProfileActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(EditStudentProfile.this, R.string.profile_updated_error, Toast.LENGTH_SHORT).show();

                                    }
                                }

                            }


                    });
                }


            });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_blank, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }}
