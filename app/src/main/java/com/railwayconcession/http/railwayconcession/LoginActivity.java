package com.railwayconcession.http.railwayconcession;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailText;
    private EditText loginPasstext;
    private Button loginBtn;
    private Button loginRegBtn;
    private Button login_forget;
    private FirebaseAuth mAuth;
    private ProgressBar loginProgress1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login Page");

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            if (mAuth.getUid().equals("eqNHxYsF1tV6fHJlYUkkN8jBMcT2")) {
                Intent intent = new Intent(LoginActivity.this, AdminDashboard.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
                finish();
            }
        }

        loginEmailText = ( EditText ) findViewById(R.id.register_email);
        loginPasstext = ( EditText ) findViewById(R.id.register_password);
        loginBtn = ( Button ) findViewById(R.id.login_btn);
        loginRegBtn = ( Button ) findViewById(R.id.login_reg_btn);
        loginProgress1 = ( ProgressBar ) findViewById(R.id.loginProgress1);
        login_forget = ( Button ) findViewById(R.id.login_forget);

        loginRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToRegister();
            }
        });

        login_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToChange();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmailText.getText().toString();
                final String password = loginPasstext.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email field cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Password field cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginProgress1.setVisibility(View.VISIBLE);

                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                loginProgress1.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        loginPasstext.setError("Password should be more than 6 letters");
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Please enter correct password", Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                    if (mAuth.getUid().equals("eqNHxYsF1tV6fHJlYUkkN8jBMcT2")) {
                                        Intent intent = new Intent(LoginActivity.this, AdminDashboard.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                        });
            }
        });
    }
            private void sendToChange() {
                Intent changeIntent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(changeIntent);
            }

            private void sendToRegister() {

                Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regIntent);
                finish();

            }

    }


