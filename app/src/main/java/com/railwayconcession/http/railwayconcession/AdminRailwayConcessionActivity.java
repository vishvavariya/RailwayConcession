package com.railwayconcession.http.railwayconcession;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdminRailwayConcessionActivity extends AppCompatActivity implements Serializable {

    CardView cardView1;

    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    List<Students> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_railway_concession);

        recyclerView = ( RecyclerView ) findViewById(R.id.recyclerView);
        cardView1=(CardView ) findViewById(R.id.cardview1);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(AdminRailwayConcessionActivity.this));

        progressDialog = new ProgressDialog(AdminRailwayConcessionActivity.this);

        progressDialog.setMessage("Loading Data from Database");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Requests");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    Students studentDetails = dataSnapshot.getValue(Students.class);

                    list.add(studentDetails);
                }


                adapter = new RecyclerViewAdapter(AdminRailwayConcessionActivity.this, list);

                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });


    }}
