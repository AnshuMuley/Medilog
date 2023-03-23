package com.example.medilog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BloodPressure extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView profileName;
    TextView textView20;
    Button submit;
    Button show_charts ;
    boolean checkF;
    boolean checkM;
    int xIndex = 1;

   FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
   DatabaseReference myRef1 = firebaseDatabase.getReference("ChartValues");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        getSupportActionBar().setTitle("Blood Pressure");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        radioGroup = findViewById(R.id.radioGroup);
        //  male = findViewById(R.id.radio_one);
        //  female = findViewById(R.id.radio_two);
        // radioButton=findViewById(R.id.radio_one);
        profileName = (TextView) findViewById(R.id.textView22);
        String p = getIntent().getStringExtra("profileName");

        profileName.setText(p);
        submit = (Button) findViewById(R.id.button17);
        show_charts = (Button) findViewById(R.id.button18);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(BloodPressure.this, BloodPressure.class);

                EditText Range = (EditText) findViewById(R.id.editTextNumberDecimal);


                SetText();

                Toast.makeText(BloodPressure.this, "Result", Toast.LENGTH_SHORT).show();
            }
        });

       show_charts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(BloodPressure.this, ShowCharts.class);
                String p =profileName.getText().toString().trim();
                EditText Syst = (EditText) findViewById(R.id.Systolic);
                EditText Diast = (EditText) findViewById(R.id.diastolic);
                String s2 = Syst.getText().toString();
                String s3 = Diast.getText().toString();
                i4.putExtra("Systolic", s2);
                i4.putExtra("Diastolic", s3);
                i4.putExtra("profileName", p);


                startActivity(i4);

                Toast.makeText(BloodPressure.this, "Charts", Toast.LENGTH_SHORT).show();
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


    public void checkButton(View v) {

        boolean checked = ((RadioButton) v).isChecked();

        RadioButton r1 = (RadioButton) findViewById(R.id.radio_one);
        RadioButton r2 = (RadioButton) findViewById(R.id.radio_two);

        switch (v.getId()) {

            case R.id.radio_one:

                if (checked) {
                    r2.setChecked(false);
                    checkF= true;
                    checkM=false;
                    // r3.setChecked(false);
                }
                break;

            case R.id.radio_two:
                if (checked) {
                    r1.setChecked(false);
                    checkF=false;
                    checkM = true;
                    //  r3.setChecked(false);
                }
                break;
        }
    }

    public void checkButton2(View u) {

        boolean checked2 = ((RadioButton) u).isChecked();

        RadioButton r3 = (RadioButton) findViewById(R.id.radio_three);
        RadioButton r4 = (RadioButton) findViewById(R.id.radio_four);
        switch (u.getId()) {

            case R.id.radio_three:

                if (checked2) {
                   // r3.setChecked(true);
                    r4.setChecked(false);
                }
                break;

            case R.id.radio_four:
                if (checked2) {
                    r3.setChecked(false);
                   // r4.setChecked(false);
                }
                break;
        }
    }
  /*  public void showCharts(ArrayList<BarEntry> dataVals) {
        BarChart barChart = (BarChart) findViewById(R.id.barchart1);
        //barChart.setVisibility(View.INVISIBLE);
        BarDataSet barDataSet = new BarDataSet(dataVals, "Analysis");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        XAxis xAxis = barChart.getXAxis();

        //color bar data set
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //text color
        barDataSet.setValueTextColor(Color.BLACK);

        //setting text size
        barDataSet.setValueTextSize(10f);
        xAxis.setGranularity(1.0f);
        xAxis.setGranularityEnabled(true);
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
    }*/


    public void SetText(){
        EditText Age = (EditText) findViewById(R.id.editTextTextPersonName4);
        EditText Syst = (EditText) findViewById(R.id.Systolic);
        EditText Diast = (EditText) findViewById(R.id.diastolic);

        String id = myRef1.push().getKey();
        String p =profileName.getText().toString().trim();
        profileName.setText(p);
        String n1 = "Systolic" ;
        String n2 = "Diastolic";
        String s1 = Age.getText().toString();
        String s2 = Syst.getText().toString();
        String s3 = Diast.getText().toString();
        Float Range_age = Float.parseFloat(s1);
        Float Systolic = Float.parseFloat(s2);
        Float diastolic = Float.parseFloat(s3);
        textView20 = (TextView) findViewById(R.id.textView20);

        DataPOint_BP d1 = new DataPOint_BP(xIndex, Systolic );
        myRef1.child(p).child(n1).child(id).setValue(d1);

        DataPoint_dias d2 = new DataPoint_dias(xIndex, Systolic );
        myRef1.child(p).child(n2).child(id).setValue(d2);




        if(checkF==true || checkM==true){
           myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                            DataPOint_BP dataPoint_bp = myDataSnapshot.getValue(DataPOint_BP.class);
                            dataVals.add(new BarEntry(xIndex, dataPoint_bp.getyValue()));
                            xIndex++;
                        }
                      //  showCharts(dataVals);
                    }
                    else  {

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                            DataPoint_dias dataPoint_dias = myDataSnapshot.getValue(DataPoint_dias.class);
                            dataVals.add(new BarEntry(xIndex, dataPoint_dias.getyValue()));
                            xIndex++;
                        }
                        //  showCharts(dataVals);
                    }
                    else  {

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            if(Systolic==110 && diastolic==68){
                    textView20.setText("Good Job . Indicates an optimal report . Normal ");
                }
                else if (Systolic > 110 && diastolic < 80) {
                    textView20.setText("Indicates an optimal report . Normal ");
                }
                else if ((Systolic > 120 && Systolic <= 129) && (diastolic <= 80)) {
                    textView20.setText("Slightly Elevated ");
                }
                else if ((Systolic >= 130 && Systolic <= 139) && (diastolic > 80 && diastolic <= 89)) {
                    textView20.setText("Hypertension - Stage 1 ");
                }
                else if ((Systolic >= 140) && (diastolic >= 90)) {
                    textView20.setText("Hypertension - Stage 2 ");
                }
                else if ((Systolic >= 180) && (diastolic >= 120)) {
                    textView20.setText("Hypertensive Crisis ");
                }

        }

        }


    }

//        Toast.makeText(this ,"Selected Radio Button :" + radioButton.getText() , Toast.LENGTH_SHORT).show();


