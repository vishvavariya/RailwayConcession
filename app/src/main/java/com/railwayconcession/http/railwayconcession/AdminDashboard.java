package com.railwayconcession.http.railwayconcession;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private LinearLayout btnRailwayConcessionAdmin, btnNoticesAdmin, btnBroadcastMessageAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard1);
        getSupportActionBar().setTitle("Admin Dashboard");

        mAuth = FirebaseAuth.getInstance();

        btnRailwayConcessionAdmin = findViewById(R.id.AdminRailwayConcession);
        btnNoticesAdmin = findViewById(R.id.AdminNotices);
        btnBroadcastMessageAdmin = findViewById(R.id.AdminBroadcastMessages);

        btnRailwayConcessionAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, AdminRailwayConcessionActivity.class);
                startActivity(intent);
            }

        });

                        btnBroadcastMessageAdmin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AdminDashboard.this, AdminBroadcastMessage.class);
                                startActivity(intent);
            }
        });
        btnNoticesAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, UploadNotices.class);
                startActivity(intent);
        }
});

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_logout_btn:
                logOut();
                return true;
            default:
                return false;
        }
    }
    private void logOut() {
        mAuth.signOut();
        sendToLogin();
    }
    private  void sendToLogin() {
        Intent LoginIntent = new Intent(AdminDashboard.this, LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }}