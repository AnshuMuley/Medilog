package com.example.medilog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class choose_Analysis extends AppCompatActivity {
    Button b1;
    Button b2;
    TextView profileName;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_analysis);
        getSupportActionBar().setTitle("Select Analysis");

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        b1=(Button) findViewById(R.id.button13);
        b2 = (Button)findViewById(R.id.button15);
        //new added
        profileName= (TextView) findViewById(R.id.textView9);
        String p = getIntent().getStringExtra("profileName");
        profileName.setText(p);

       // profileName=(TextView) findViewById(R.id.editTextTextPersonName);



        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
           //    Intent i3 = new Intent(choose_Analysis.this,Analysis.class);
                //    startActivity(i3);
             //   Toast.makeText(choose_Analysis.this, "Moved to Select Analysis", Toast.LENGTH_SHORT).show();

                Intent i3 = new Intent(choose_Analysis.this,Analysis.class);
                String p =profileName.getText().toString().trim();
                i3.putExtra("profileName", p);
                startActivity(i3);
                Toast.makeText(choose_Analysis.this, "Moved to Upload Reports", Toast.LENGTH_SHORT).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //    Intent i3 = new Intent(choose_Analysis.this,Analysis.class);
                //    startActivity(i3);
                //   Toast.makeText(choose_Analysis.this, "Moved to Select Analysis", Toast.LENGTH_SHORT).show();

                Intent i4 = new Intent(choose_Analysis.this,BloodPressure.class);
                String p =profileName.getText().toString().trim();
                i4.putExtra("profileName", p);
                startActivity(i4);
                Toast.makeText(choose_Analysis.this, "Moved to Upload Reports", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}