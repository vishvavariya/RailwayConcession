package com.railwayconcession.http.railwayconcession;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    RadioButton radioButton;
    RadioGroup radiogroup;
    private EditText reg_email;
    private EditText reg_pass;
    private EditText reg_confirmpass;
    private EditText register_SAPID;
    private EditText register_Name;
    private EditText register_mobile;
    private TextView register_dob;
    private Button register_btn;
    private Button login_btn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;



    @SuppressLint("WrongViewCast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registration Page");

        TextView reg_dob = (TextView) findViewById(R.id.reg_dob);

        reg_dob.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }

        });

        mAuth = FirebaseAuth.getInstance();
        reg_email = (EditText) findViewById(R.id.register_email);
        reg_pass = (EditText) findViewById(R.id.register_password);
        reg_confirmpass= (EditText) findViewById(R.id.register_confirmpassword);
        register_btn = (Button) findViewById(R.id.signup_btn);
        login_btn = (Button) findViewById(R.id.register_login_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        register_SAPID= (EditText) findViewById(R.id.reg_SAPID);
        register_Name= (EditText) findViewById(R.id.reg_Name);
        register_mobile= (EditText) findViewById(R.id.reg_mobile);
        register_dob= (TextView) findViewById(R.id.reg_dob);
        radiogroup = findViewById(R.id.radioGrp);
        login_btn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               sendToLogin(); }
                                       });


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = reg_email.getText().toString();
                String pass = reg_pass.getText().toString();
                String confirm_pass = reg_confirmpass.getText().toString();
                radiogroup=findViewById(R.id.radioGrp);
                int id = radiogroup.getCheckedRadioButtonId();
                radioButton = findViewById(id);
                String Name = register_Name.getText().toString();
                String Email = reg_email.getText().toString();
                String Password = reg_pass.getText().toString();
                String Mobile = register_mobile.getText().toString();
                String SAPID = register_SAPID.getText().toString();
                String DOB = register_dob.getText().toString();


                if (Name.isEmpty()) {
                    register_Name.setError("Name cannot be empty");
                    register_Name.requestFocus();
                } else {
                    register_Name.setError(null);
                }
                if (email.isEmpty()) {
                    reg_email.setError(getString(R.string.input_error_email));
                    reg_email.requestFocus();
                    return;
                } else {
                    reg_email.setError(null);
                }
                if (Password.isEmpty()) {
                    reg_pass.setError("Please enter password");
                    reg_pass.requestFocus();
                    return;
                } else {
                    reg_pass.setError(null);
                }
                if (Password.length() < 6) {
                    reg_pass.setError("Password length should be more than 6 characters");
                    reg_pass.requestFocus();
                    return;
                } else {
                    reg_pass.setError(null);
                }
                if (confirm_pass.isEmpty()) {
                    reg_confirmpass.setError("Please enter password again");
                    reg_confirmpass.requestFocus();
                    return;
                } else {
                    reg_confirmpass.setError(null);
                }

                if (Mobile.isEmpty()) {
                    register_mobile.setError("Enter a mobile number");
                    register_mobile.requestFocus();
                    return;
                } else {
                    register_mobile.setError(null);
                }
                if (SAPID.isEmpty()) {
                    register_SAPID.setError("Enter SAPID");
                    register_SAPID.requestFocus();
                    return;
                } else {
                    register_SAPID.setError(null);
                }
                if (Mobile.length() != 10) {
                    register_mobile.setError("Enter valid mobile number");
                    register_mobile.requestFocus();
                    return;
                } else {
                    register_mobile.setError(null);
                }
                if (SAPID.length() != 11) {
                    register_SAPID.setError("Enter a valid SAP ID");
                    register_SAPID.requestFocus();
                    return;
                } else {
                    register_SAPID.setError(null);
                }
               if (DOB.isEmpty()) {
                    register_dob.setError("Enter DOB");
                    register_dob.requestFocus();
                    return;
                } else {
                    register_dob.setError(null);
                }
                if(radiogroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
                    radiogroup.requestFocus();
                    return;
                }


                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass)) {
                    if (pass.equals(confirm_pass)) {
                        progressBar.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);


                                if (task.isSuccessful()) {
                                        String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                                        String Name = register_Name.getText().toString();
                                        String Email = reg_email.getText().toString();
                                        String Password = reg_pass.getText().toString();
                                        String Mobile = register_mobile.getText().toString();
                                        String SAPID = register_SAPID.getText().toString();
                                        String DOB = register_dob.getText().toString();
                                    String cat = (String) radioButton.getText();


                                    Map<String, String> newPost = new HashMap<>();
                                        newPost.put("Name", Name);
                                        newPost.put("Email", Email);
                                        newPost.put("Password", Password);
                                        newPost.put("Mobile", Mobile);
                                        newPost.put("SAPID", SAPID);
                                        newPost.put("DOB", DOB);
                                        newPost.put("Gender",cat);


                                        current_user_db.setValue(newPost);
                                        sendToLogin();
                                }
                                else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                                }

                            }
                        });


                    } else {
                        Toast.makeText(RegisterActivity.this, "Confirm Password and password does not match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView reg_dob = (TextView) findViewById(R.id.reg_dob);
        reg_dob.setText(currentDateString);
    }



    @Override
            protected void onStart() {
            super.onStart();

            FirebaseUser currentUser= mAuth.getCurrentUser();
            if (currentUser !=null) {
                sendToMain();
            }
            }


            private void sendToMain() {
                Intent mainIntent = new Intent(RegisterActivity.this, NavigationDrawerActivity.class);
                startActivity(mainIntent);
                finish();

            }
    private void sendToLogin() {

        Intent logIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(logIntent);
        finish();

    }

    public void checkButton(View view) {
        int id= radiogroup.getCheckedRadioButtonId();
        radioButton= findViewById(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }



}
