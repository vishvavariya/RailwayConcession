package com.railwayconcession.http.railwayconcession;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticesActivity extends AppCompatActivity {
    RecyclerView rvViewNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);
        getSupportActionBar().setTitle("Notices");
        ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvViewNotes = findViewById(R.id.rvViewNotes);
        rvViewNotes.setLayoutManager(new LinearLayoutManager(NoticesActivity.this));
        ViewNoticesAdapter adapter = new ViewNoticesAdapter(rvViewNotes, NoticesActivity.this, new ArrayList<String>(), new ArrayList<String>());
        rvViewNotes.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notices");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot notesIds : dataSnapshot.getChildren()) {
                    Notices notices = notesIds.getValue(Notices.class);

                    String fileName = notices.getFileName(); //returns file name
                    String url = notices.getFileUrl(); //returns url

                    (( ViewNoticesAdapter ) rvViewNotes.getAdapter()).update(fileName, url);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
            public boolean onOptionsItemSelected(MenuItem item) {
                Intent myIntent = new Intent(NoticesActivity.this, NavigationDrawerActivity.class);
                startActivity(myIntent);
                return true;
            }}
