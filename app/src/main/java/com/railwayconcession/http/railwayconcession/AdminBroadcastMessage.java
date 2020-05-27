package com.railwayconcession.http.railwayconcession;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminBroadcastMessage extends AppCompatActivity {

    private EditText etUpdateReminder, etUpdateBroadcastMessage;
    private Button btnUpdateBroadcast;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_broadcast_message);
        getSupportActionBar().setTitle("Broadcast");


        etUpdateReminder = findViewById(R.id.etUpdateReminder);
        etUpdateBroadcastMessage = findViewById(R.id.etUpdateBroadcastMessage);
        btnUpdateBroadcast = findViewById(R.id.btnUpdateBroadcast);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("Broadcast");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Broadcast broadcast = dataSnapshot.getValue(Broadcast.class);

                etUpdateReminder.setText(broadcast.getReminder());
                etUpdateBroadcastMessage.setText(broadcast.getBroadcastmessage());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdminBroadcastMessage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

        btnUpdateBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String reminder = etUpdateReminder.getText().toString().trim();
                final String broadcast = etUpdateBroadcastMessage.getText().toString().trim();


                databaseReference.child("reminder").setValue(reminder).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            databaseReference.child("broadcastmessage").setValue(broadcast).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AdminBroadcastMessage.this, R.string.broadcast_updated_success, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(AdminBroadcastMessage.this, AdminDashboard.class));
                                        finish();
                                    } else {
                                        Toast.makeText(AdminBroadcastMessage.this, R.string.broadcast_updated_error, Toast.LENGTH_LONG).show();

                                    }
                                }

                            });
                        }

                    }
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_blank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
