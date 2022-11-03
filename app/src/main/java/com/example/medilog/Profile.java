package com.example.medilog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    Button b1;
    Button b2;
    Button b4;
    Button b5;
    Button LogOut;
    TextView profileName ;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Profile");
        b1=(Button) findViewById(R.id.button);
        b2= (Button) findViewById(R.id.button2);
        b4=(Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        LogOut=(Button) findViewById(R.id.button6);
        profileName=(TextView) findViewById(R.id.editTextTextPersonName);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser= firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Datab1");
        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String fnameR = String.valueOf(dataSnapshot.child("fname").getValue());
                    String lnameR = String.valueOf(dataSnapshot.child("lname").getValue());
                    String fullname = fnameR + " " + lnameR;
                    profileName.setText(fullname);
                }
                else{
                    Toast.makeText(Profile.this, "Try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        LogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Intent i7 = new Intent(Profile.this, Login.class);
                //  startActivity(i7);
                // Toast.makeText(Profile.this, "", Toast.LENGTH_SHORT).show();
                //  finish();
                firebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Profile.this , Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });


        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i3 = new Intent(Profile.this,UploadReports.class);
                startActivity(i3);

                Toast.makeText(Profile.this, "Moved to Upload Reports", Toast.LENGTH_SHORT).show();
            }


        });
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i4 = new Intent(Profile.this, Analysis.class);
                startActivity(i4);
                Toast.makeText(Profile.this, "Moved to Analysis", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i5 = new Intent(Profile.this, Appointments.class);
                startActivity(i5);
                Toast.makeText(Profile.this, "Create Appointments", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i6 = new Intent(Profile.this, UploadPrescriptions.class);
                startActivity(i6);
                Toast.makeText(Profile.this, "Upload Prescriptions", Toast.LENGTH_SHORT).show();
                finish();
            }
        });







    }
}