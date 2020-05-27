package com.railwayconcession.http.railwayconcession;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout btnRailwayConcession, btnNotices, btnBroadcastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        final Toolbar toolbar = ( Toolbar ) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student Dashboard");

        btnRailwayConcession = findViewById(R.id.AdminRailwayConcession);
        btnNotices = findViewById(R.id.NoticesLinear);
        btnBroadcastMessage = findViewById(R.id.BroadcastMessageLinear);

        btnRailwayConcession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationDrawerActivity.this, RailwayConcessionActivity.class);
                startActivity(intent);

            }

        });
                btnNotices.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(NavigationDrawerActivity.this, NoticesActivity.class);
                        startActivity(intent);
                    }

                });
                        btnBroadcastMessage.setOnClickListener(new View.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(View view) {
                                                                       Intent intent = new Intent(NavigationDrawerActivity.this, BroadcastMessageActivity.class);
                                                                       startActivity(intent);
                                                                   }
                                                               });


        DrawerLayout drawer = ( DrawerLayout ) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = ( NavigationView ) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = ( DrawerLayout ) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            Intent pActivity = new Intent(NavigationDrawerActivity.this, ProfileActivity.class);
            startActivity(pActivity);

        } else if (id == R.id.nav_change_password) {

            Intent cpActivity = new Intent(NavigationDrawerActivity.this, ChangePasswordActivity.class);
            startActivity(cpActivity);

        } else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(NavigationDrawerActivity.this, LoginActivity.class));
            Toast.makeText(NavigationDrawerActivity.this, "Logout", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.nav_about) {

            Intent aActivity = new Intent(NavigationDrawerActivity.this, AboutActivity.class);
            startActivity(aActivity);

        }
        else if (id == R.id.nav_calculator) {

            Intent aActivity = new Intent(NavigationDrawerActivity.this, CgpaCalculator.class);
            startActivity(aActivity);

        }

        DrawerLayout drawer = ( DrawerLayout ) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
