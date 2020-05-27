package com.railwayconcession.http.railwayconcession;
import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private EditText etChangePassword;
    private Button btnChangePassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");

        etChangePassword = findViewById(R.id.etChangePassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        mAuth = FirebaseAuth.getInstance();

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etChangePassword.getText().toString().trim();

                if(email.equals("") || email.isEmpty() || email.equals(null)){
                    etChangePassword.setError(getString(R.string.input_error_email));
                    etChangePassword.requestFocus();
                }else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgetPassword.this,R.string.password_reset_email_success,Toast.LENGTH_SHORT).show();
                                finish();
                                mAuth.signOut();
                                startActivity(new Intent(ForgetPassword.this,LoginActivity.class));
                            }else {
                                Toast.makeText(ForgetPassword.this,R.string.password_reset_email_failure,Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_blank, menu);
        return true;
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
