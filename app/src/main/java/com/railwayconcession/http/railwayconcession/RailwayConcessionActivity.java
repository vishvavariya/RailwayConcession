package com.railwayconcession.http.railwayconcession;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class RailwayConcessionActivity extends AppCompatActivity {

private LinearLayout LinearAppReq;
private LinearLayout LinearAppStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway_concession);
        ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        LinearAppReq = findViewById(R.id.LinearAppReq);
        LinearAppStatus = findViewById(R.id.LinearAppStatus);


        LinearAppReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RailwayConcessionActivity.this, ApplicationRequest.class);
                startActivity(intent);
            }
        });

                LinearAppStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RailwayConcessionActivity.this, ApplicationStatus.class);
                        startActivity(intent);

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
