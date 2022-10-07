package com.example.medilog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
    Button b1;
    Button b2;
    Button b4;
    Button b5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Profile");

        b1=(Button) findViewById(R.id.button);
        b2= (Button) findViewById(R.id.button2);
        b4=(Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);

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