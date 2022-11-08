package com.example.medilog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DownloadPrescriptionImages extends AppCompatActivity {

    private ArrayList<String> urlList = new ArrayList<>();
    private  ImageAdapter_P imageAdapter_P;
    TextView profileName ;


    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_prescription_images);
        profileName=(TextView) findViewById(R.id.textView8);
        initRecyclerView();
        loadURLs();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        imageAdapter_P = new ImageAdapter_P(urlList, this);
        recyclerView.setAdapter(imageAdapter_P);
    }

    private void loadURLs() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null && snapshot.hasChildren()) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        urlList.add(dataSnapshot.getValue().toString());
                    }
                    imageAdapter_P.setUpdatedData(urlList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        String p = getIntent().getStringExtra("profileName");
        profileName.setText(p);
        DatabaseReference dbRef = database.getReference().child("prescription_images").child(p);
        dbRef.addValueEventListener(listener);
    }
}