package com.example.medilog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowCharts extends AppCompatActivity {
    TextView profileName;
    TextView content ;
    Button insert ;
    EditText xValue;
    EditText yValue ;
    BarChart barChart;
   //BarDataSet barDataSet = new BarDataSet(null , null);
   // ArrayList<BarDataSet> barDataSets = new ArrayList<>();
   // BarData barData;


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef1 = firebaseDatabase.getReference("ChartValues");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_charts);
        getSupportActionBar().setTitle("Show Charts");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        profileName = (TextView) findViewById(R.id.textView10);
        content = (TextView)findViewById(R.id.textView11);
        String p = getIntent().getStringExtra("profileName");

        xValue = (EditText) findViewById(R.id.edittext13);
        yValue = (EditText) findViewById(R.id.edittext19);
        barChart = (BarChart)findViewById(R.id.barchart1);


        profileName.setText(p);
        insert = (Button)findViewById(R.id.button21);
        insertData();


    }

    private void insertData() {
        String x1 = "Systolic_x";
        String y1 = "Diastolic_y";

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = myRef1.push().getKey();
                int x = Integer.parseInt(xValue.getText().toString());
                int y = Integer.parseInt(yValue.getText().toString());

                DataPoint_2 dataPoint_2 = new DataPoint_2(x,y);
                myRef1.child(id).setValue(dataPoint_2);

                retrieveData();
            }
        });
    }
    private void retrieveData(){
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();

                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                        DataPoint_2 dataPoint_2 = myDataSnapshot.getValue(DataPoint_2.class);
                        dataVals.add(new BarEntry(dataPoint_2.getxValue(),dataPoint_2.getyValue()));
                    }
                    showCharts(dataVals);
                }
                else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void showCharts(ArrayList<BarEntry> dataVals){
        BarChart barChart = (BarChart) findViewById(R.id.barchart1);
       // barDataSet.setValues(dataVals);
      //  barDataSet.setLabel("Dataset 1");

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





    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}