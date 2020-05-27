package com.railwayconcession.http.railwayconcession;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BroadcastMessageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private TextView tvReminder, tvBroadcastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Broadcast Messages");

        tvReminder = findViewById(R.id.tvReminder);
        tvBroadcastMessage = findViewById(R.id.tvBroadcastMessage);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("Broadcast");




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Broadcast broadcast = dataSnapshot.getValue(Broadcast.class);

                tvReminder.setText(broadcast.getReminder());
                tvBroadcastMessage.setText(broadcast.getBroadcastmessage());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BroadcastMessageActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

        tvReminder.setMovementMethod(new ScrollingMovementMethod());
        tvBroadcastMessage.setMovementMethod(new ScrollingMovementMethod());
        }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(BroadcastMessageActivity.this , NavigationDrawerActivity.class);
        startActivity(myIntent);
        return true;
    }
}
