package com.railwayconcession.http.railwayconcession;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;


public class UploadNotices extends AppCompatActivity {

    private static final int RESULT_LOAD_FILE = 1;
    private Button btnUploadFiles;
    private RecyclerView rvUploadNotices;

    private StorageReference mStorage;
    private Uri fileUri;

    private List<String> fileNameList;
    private List<String> fileDoneList;

    private UploadFilesAdapter uploadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notices);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upload Notices");


        btnUploadFiles = findViewById(R.id.btnUploadFiles);
        rvUploadNotices = findViewById(R.id.rvUploadNotices);
        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();
        uploadListAdapter = new UploadFilesAdapter(fileNameList, fileDoneList);

        mStorage = FirebaseStorage.getInstance().getReference();

        rvUploadNotices.setLayoutManager(new LinearLayoutManager(this));
        rvUploadNotices.setHasFixedSize(true);
        rvUploadNotices.setAdapter(uploadListAdapter);

        btnUploadFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Files"), RESULT_LOAD_FILE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_FILE && resultCode == RESULT_OK) {
            final DatabaseReference noticesRef = FirebaseDatabase.getInstance().getReference("Notices");
            //handle multiple selections
            if (data.getClipData() != null) {
                int totalItemSelected = data.getClipData().getItemCount();

                for (int i = 0; i < totalItemSelected; i++) {
                    fileUri = data.getClipData().getItemAt(i).getUri();
                    final String fileName = getFileName(fileUri) + ".pdf";
                    final String fileNameWithoutPDF = getFileName(fileUri) + "";
                    fileNameList.add(fileName);
                    fileDoneList.add("uploading");
                    uploadListAdapter.notifyDataSetChanged();


                    final StorageReference fileToUpload = mStorage.child("Documents").child(fileName);
                    final int finalI = i;

                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileToUpload.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    fileDoneList.remove(finalI);
                                    fileDoneList.add(finalI, "done");

                                    uploadListAdapter.notifyDataSetChanged();
                                    Notices notices = new Notices(fileNameWithoutPDF, uri.toString());
                                    String uploadId = noticesRef.push().getKey();
                                    noticesRef.child(uploadId).setValue(notices);
                                    Toast.makeText(UploadNotices.this, "Upload Success", Toast.LENGTH_SHORT).show();
                                }
                            })

                                    .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadNotices.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                    });
                        }
                    });
                }
                Toast.makeText(this, "Selected Multiple Files", Toast.LENGTH_SHORT).show();
            } else if (data.getData() != null) {       //handle single selection
                Uri fileUri = data.getData();
                final String fileName = getFileName(fileUri) + ".pdf";
                final String fileNameWithoutPDF = getFileName(fileUri) + "";
                fileNameList.add(fileName);
                fileDoneList.add("uploading");
                uploadListAdapter.notifyDataSetChanged();

                final StorageReference fileToUpload = mStorage.child("Documents").child(fileName);

                fileToUpload.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        fileDoneList.remove(0);
                        fileDoneList.add(0, "done");
                        uploadListAdapter.notifyDataSetChanged();
                        Notices notices = new Notices(fileNameWithoutPDF, uri.toString());
                        String uploadId = noticesRef.push().getKey();
                        noticesRef.child(uploadId).setValue(notices);
                        Toast.makeText(UploadNotices.this, "Upload Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadNotices.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(this, "Selected Single File", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    @Override
    public void onBackPressed() {
        finish();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), AdminDashboard.class);
        startActivityForResult(myIntent, 0);
        return true;
    }



}

