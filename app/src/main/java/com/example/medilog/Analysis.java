package com.example.medilog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Analysis extends AppCompatActivity {
    Spinner spinner;
    EditText editText;
    Button button;
    TextView textView2;
    TextView textView6;
    int xIndex = 1;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef1 = firebaseDatabase.getReference("ChartValues");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        getSupportActionBar().setTitle("Analysis");

        Spinner spinnerTest = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Analysis.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Tests));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTest.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        button = (Button) findViewById(R.id.button8);
        textView2 = (TextView) findViewById(R.id.textView2);
        spinner = (Spinner) findViewById(R.id.spinner2);
        editText = (EditText) findViewById(R.id.editTextNumberDecimal);
        textView6 = (TextView) findViewById(R.id.textView6);
        String p = getIntent().getStringExtra("profileName");
        textView6.setText(p);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(Analysis.this, Analysis.class);

                EditText Range = (EditText) findViewById(R.id.editTextNumberDecimal);
                String regex = "^\\d*\\.\\d+|\\d+\\.\\d*$";
                String test = spinner.getSelectedItem().toString();
                String s = Range.getText().toString();
                i3.putExtra("test", test);
                i3.putExtra("range", s);
                i3.putExtra("profileName", p);

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = firebaseDatabase.getReference("ChartValues");
                DatabaseReference myRef = myRef1.child(p);

                SetText();

                Toast.makeText(Analysis.this, "Moved to Barchart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(Analysis.this, Profile.class);
        startActivity(myIntent);
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showCharts(ArrayList<BarEntry> dataVals){
        BarChart barChart = (BarChart) findViewById(R.id.barchart1);
        //barChart.setVisibility(View.INVISIBLE);
        BarDataSet barDataSet = new BarDataSet(dataVals,"Analysis");
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
    }

    public void SetText(){
        EditText Range = (EditText) findViewById(R.id.editTextNumberDecimal);
        String test = spinner.getSelectedItem().toString();
        String s = Range.getText().toString();
        Double range = Double.parseDouble(s);
        String id = myRef1.push().getKey();
        final String yV1 = editText.getText().toString().trim();
        Float y = Float. parseFloat(yV1);
        String p = getIntent().getStringExtra("profileName");
        DataPoint d1 = new DataPoint(xIndex, y );
        myRef1.child(p).child(test).child(id).setValue(d1);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = firebaseDatabase.getReference("ChartValues");
        // DatabaseReference myRef = myRef1.child(p).child(id);
        DatabaseReference myRef = myRef1.child(p).child(test);
        if (test.equals("Fasting")) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                            DataPoint dataPoint = myDataSnapshot.getValue(DataPoint.class);
                            dataVals.add(new BarEntry(xIndex, dataPoint.getyValue()));
                            xIndex++;
                        }
                        showCharts(dataVals);
                    }
                    else  {

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if (range >= 70 && range <= 100) {
                textView2.setText("Indicates an optimal report . Blood sugar level is balanced in blood.");
            } else if (range <= 70) {
                textView2.setText("Indicates Low sugar level.");
            } else if (range >= 100 && range <= 125) {
                textView2.setText("Non Diabetic person - Slightly Higher Sugar level . Indicates a person is Pre Diabetic.\n" +
                        "\n**Condition - This may happen suddenly during a major illness or injury or may happen over a longer period " +
                        "and be caused by a chronic disease (Diabetes I or II.)**\n" +
                        "\nDiabetic person - Indicates low sugar level that means the medications are lowering the blood " +
                        " sugar level too much or over production of insulin.");
            } else if (range >= 126) {
                if (126 <= range && range <= 180) {
                    textView2.setText("Indicates an optimal report for a Diabetic person.\nPrescribed Medication should be continued. \n " +
                            "Precautions must be taken for eating habits");

                } else if (range > 180) {
                    textView2.setText("High levels of fasting blood sugar suggest the body was not able to lower blood sugar levels\n" +
                            " this points to insulin resistance , inadequate insulin production , or in some cases, both .\n" +
                            " * Must consult to a specialist. *");
                }
            }
        }

        if (test.equals("Posting")) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                            DataPoint dataPoint = myDataSnapshot.getValue(DataPoint.class);
                            dataVals.add(new BarEntry(xIndex, dataPoint.getyValue()));
                            xIndex++;
                        }
                        showCharts(dataVals);
                    }
                    else  {

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if (range <= 70) {
                textView2.setText("Indicates Low sugar level.");
            } else if (range >= 71 && range <= 140) {
                textView2.setText("Indicates an optimal Post Meal report .");
            } else if (range >= 140 && range <= 199) {
                textView2.setText("Diabetic  -  It indicates an optimal report for a diabetic person .\n" +
                        "\nNon Diabetic - A person may be Pre-Diabetic\n" +
                        "\n* Consulting a specialist is recommended. *");
            } else if (range >= 200) {
                textView2.setText("Indicates a person is Diabetic.\n" +
                        "Condition -  This may arise from under production of Insulin.\n" +
                        "High intake of carbohydrates or Glucose .\n" +
                        "\n * Consult a specialist and get your medicines revised." +
                        "\nPrecautions must be taken in your eating habits. *");
            }
        }
        if (test.equals("Random")) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                            DataPoint dataPoint = myDataSnapshot.getValue(DataPoint.class);
                            dataVals.add(new BarEntry(xIndex, dataPoint.getyValue()));
                            xIndex++;
                        }
                        showCharts(dataVals);
                    }
                    else  {

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if (range <= 70) {
                textView2.setText("Indicates Low sugar level.\n" + "Immediate intake of juice, candy, glucose is highly recommended. ");
            } else if (range >= 71 && range <= 139) {
                textView2.setText("Indicates an optimal Random report .");
            } else if (range >= 140 && range <= 199) {
                textView2.setText(" Indicates a person is Pre-Diabetic. \n" + "Consulting a specialist is recommended.\n" + "Generally Doctors recommend this test to diagnose Diabetes.\n" + "To Help confirm the Diagnosis,the doctor may also order different type of test. ");
            } else if (range >= 200) {
                textView2.setText("Indicates a person is Diabetic.\n" + "Consulting a specialist is recommended.\n" + "Generally Doctors recommend this test to diagnose Diabetes.\n" + "To Help confirm the Diagnosis,the doctor may also order different type of test. ");
            }
        }
        if (test.equals("A1c Test")) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                            DataPoint dataPoint = myDataSnapshot.getValue(DataPoint.class);
                            dataVals.add(new BarEntry(xIndex, dataPoint.getyValue()));
                            xIndex++;
                        }
                        showCharts(dataVals);
                    }
                    else  {

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if (range <= 5.7) {
                textView2.setText("Indicates an optimal A1c test report .");
            } else if (range > 5.7 && range <= 6.4) {
                textView2.setText(" Indicates a person is Pre-Diabetic. \n" + "Consulting a specialist is recommended.");
            } else if (range >= 6.5) {
                textView2.setText(" Indicates a person is Diabetic. \n" + "Consulting a specialist is recommended.");
            }
        }
    }
}