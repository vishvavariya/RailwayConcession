package com.railwayconcession.http.railwayconcession;

import android.arch.persistence.room.Database;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApplicationRequest extends AppCompatActivity {
    private TextView StudentName, StudentEmail,StudentDOB,StudentMobile,StudentGender,StudentSAPID;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    RadioButton radioButton,radioButton1,radioButton2;
    RadioGroup radiogroup,radiogroup1,radiogroup2;
    private Button btnStudentSubmit;
    private EditText StudentFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_application_request);
        getSupportActionBar().setTitle("Application Request");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StudentName = findViewById(R.id.StudentName);
        StudentEmail = findViewById(R.id.StudentEmail);
        StudentDOB = findViewById(R.id.StudentDOB);
        StudentSAPID = findViewById(R.id.StudentSAPID);
        StudentMobile = findViewById(R.id.StudentMobile);
        StudentGender = findViewById(R.id.StudentGender);
        StudentFrom = (EditText) findViewById(R.id.StudentFrom);
        radiogroup=findViewById(R.id.radioGrp);
        int id = radiogroup.getCheckedRadioButtonId();
        radioButton = findViewById(id);
        radiogroup1=findViewById(R.id.radioGrp1);
        int id1 = radiogroup1.getCheckedRadioButtonId();
        radioButton1 = findViewById(id1);
        radiogroup2=findViewById(R.id.radioGrp2);
        int id2 = radiogroup2.getCheckedRadioButtonId();
        radioButton2 = findViewById(id2);
        btnStudentSubmit = ( Button ) findViewById(R.id.StudentSubmit);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue(String.class);
                String email = dataSnapshot.child("Email").getValue(String.class);
                String SAPID = dataSnapshot.child("SAPID").getValue(String.class);
                String Mobile = dataSnapshot.child("Mobile").getValue(String.class);
                String Gender = dataSnapshot.child("Gender").getValue(String.class);
                String DOB = dataSnapshot.child("DOB").getValue(String.class);

                StudentName.setText("" + name);
                StudentEmail.setText("" + email);
                StudentMobile.setText("" + Mobile);
                StudentSAPID.setText("" + SAPID);
                StudentGender.setText("" + Gender);
                StudentDOB.setText("" + DOB);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ApplicationRequest.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        btnStudentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String uid=mAuth.getCurrentUser().getUid().trim();
                final String Name = StudentName.getText().toString().trim();
                final String SAPID = StudentSAPID.getText().toString().trim();
                final String Mobile = StudentMobile.getText().toString().trim();
                final String Email = StudentEmail.getText().toString().trim();
                final String DOB = StudentDOB.getText().toString().trim();
                final String Gender = StudentGender.getText().toString().trim();
                final String from = StudentFrom.getText().toString().trim();

                int id = radiogroup.getCheckedRadioButtonId();
                radioButton = findViewById(id);
                int id1 = radiogroup1.getCheckedRadioButtonId();
                radioButton1 = findViewById(id1);
                int id2 = radiogroup2.getCheckedRadioButtonId();
                radioButton2 = findViewById(id2);


                if (from.isEmpty()) {
                    StudentFrom.setError("Enter where are you from");
                    StudentFrom.requestFocus();
                    return;
                } else {
                    StudentFrom.setError(null);
                }

                if(radiogroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select Suburb", Toast.LENGTH_SHORT).show();
                    radiogroup.requestFocus();
                    return;
                }
                if(radiogroup1.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select Coach", Toast.LENGTH_SHORT).show();
                    radiogroup1.requestFocus();
                    return;
                }
                if(radiogroup2.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select Period", Toast.LENGTH_SHORT).show();
                    radiogroup2.requestFocus();
                    return;
                }

                final DatabaseReference batchReference = firebaseDatabase.getReference().child("Requests").child(mAuth.getCurrentUser().getUid());
                final DatabaseReference batchReference1=firebaseDatabase.getReference().child("Status").child(mAuth.getCurrentUser().getUid());
                batchReference.child("Name").setValue(Name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String sub = (String) radioButton.getText();
                        String Coach = (String) radioButton1.getText();
                        String period = (String) radioButton2.getText();

                        if (task.isSuccessful()) {
                            batchReference.child("Name").setValue(Name);
                            batchReference.child("SAPID").setValue(SAPID);
                            batchReference.child("Mobile").setValue(Mobile);
                            batchReference.child("Email").setValue(Email);
                            batchReference.child("DOB").setValue(DOB);
                            batchReference.child("Gender").setValue(Gender);
                            batchReference.child("From").setValue(from);
                            batchReference.child("Suburb").setValue(sub);
                            batchReference.child("Coach").setValue(Coach);
                            batchReference.child("Period").setValue(period);
                            batchReference.child("UID").setValue(uid);

                            batchReference1.child("Name").setValue(Name);
                            batchReference1.child("SAPID").setValue(SAPID);
                            batchReference1.child("Mobile").setValue(Mobile);
                            batchReference1.child("Email").setValue(Email);
                            batchReference1.child("Remarks").setValue("SUBMITTED");
                            if (task.isSuccessful()) {
                                Toast.makeText(ApplicationRequest.this, "Your application has been submitted", Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(ApplicationRequest.this, ApplicationStatus.class);
                                startActivity(myIntent);

                                finish();
                            } else {
                                Toast.makeText(ApplicationRequest.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        }

                    }


                });
            }


        });

    }
        public boolean onOptionsItemSelected (MenuItem item) {
            Intent myIntent = new Intent(getApplicationContext(), RailwayConcessionActivity.class);
            startActivityForResult(myIntent, 0);
            return true;
        }

    public void checkButton(View view) {
        int id= radiogroup.getCheckedRadioButtonId();
        radioButton= findViewById(id);
    }
    public void checkButton1(View view) {
        int id1= radiogroup.getCheckedRadioButtonId();
        radioButton1= findViewById(id1);
    }
    public void checkButton2(View view) {
        int id2= radiogroup.getCheckedRadioButtonId();
        radioButton2= findViewById(id2);
    }

}


