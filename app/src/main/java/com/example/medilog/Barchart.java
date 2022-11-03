package com.example.medilog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Range;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Barchart extends AppCompatActivity {
    ArrayList barArraylist;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        BarChart barChart = (BarChart) findViewById(R.id.barchart1);
        textView2 = (TextView) findViewById(R.id.textView2);
        getData();
        BarDataSet barDataSet = new BarDataSet(barArraylist,"Cambo Tutorial");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        //color bar data set
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //text color
        barDataSet.setValueTextColor(Color.BLACK);

        //setting text size
        barDataSet.setValueTextSize(16f);
        String test = getIntent().getStringExtra("test");
        String rge = getIntent().getStringExtra("range");
        Double range=Double.parseDouble(rge);
        if(test.equals("Fasting")) {
            if (range >= 70 && range <= 100) {
                textView2.setText("Indicates an optimal report . Blood sugar level is balanced in blood.");
            }
            else if(range<=70){
                textView2.setText("Indicates Low sugar level.");
            }
            else if(range>=100 && range<=125)
            {
                textView2.setText("Non Diabetic person - Slightly Higher Sugar level . Indicates a person is Pre Diabetic.\n"+
                        "\n**Condition - This may happen suddenly during a major illness or injury or may happen over a longer period "+
                        "and be caused by a chronic disease (Diabetes I or II.)**\n"+
                        "\nDiabetic person - Indicates low sugar level that means the medications are lowering the blood "+
                        " sugar level too much or over production of insulin.");
            }
            else if(range>=126) {
                // textView.setText("Indicates a person is Diabetic");
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

        if(test.equals("Posting")){
            if(range<=70){
                textView2.setText("Indicates Low sugar level.");
            }
            else if(range>=71 && range<=140){
                textView2.setText("Indicates an optimal Post Meal report .");
            }
            else if(range>=140 && range<=199){
                textView2.setText("Diabetic  -  It indicates an optimal report for a diabetic person .\n"+
                        "\nNon Diabetic - A person may be Pre-Diabetic\n"+
                        "\n* Consulting a specialist is recommended. *");
            }
            else if(range>=200){
                textView2.setText("Indicates a person is Diabetic.\n"+
                        "Condition -  This may arise from under production of Insulin.\n"+
                        "High intake of carbohydrates or Glucose .\n"+
                        "\n * Consult a specialist and get your medicines revised."+
                        "\nPrecautions must be taken in your eating habits. *");
            }
        }
        if(test.equals("Random")){

            if(range<=70){
                textView2.setText("Indicates Low sugar level.\n"+"Immediate intake of juice, candy, glucose is highly recommended. ");
            }
            else if(range>=71 && range<=139)
            {
                textView2.setText("Indicates an optimal Random report .");
            }
            else if(range>=140 && range<=199)
            {
                textView2.setText(" Indicates a person is Pre-Diabetic. \n"+"Consulting a specialist is recommended.\n"+"Generally Doctors recommend this test to diagnose Diabetes.\n"+"To Help confirm the Diagnosis,the doctor may also order different type of test. " );
            }
            else if(range>=200)
            {
                textView2.setText("Indicates a person is Diabetic.\n"+"Consulting a specialist is recommended.\n"+"Generally Doctors recommend this test to diagnose Diabetes.\n"+"To Help confirm the Diagnosis,the doctor may also order different type of test. " );
            }
        }
        if(test.equals("A1c Test"))
        {
            if (range <= 5.7) {
                textView2.setText("Indicates an optimal A1c test report .");
            } else if (range > 5.7 && range <= 6.4) {
                textView2.setText(" Indicates a person is Pre-Diabetic. \n" + "Consulting a specialist is recommended.");
            } else if (range >= 6.5) {
                textView2.setText(" Indicates a person is Diabetic. \n" + "Consulting a specialist is recommended.");
            }
            //  }
        }
    }

    private void getData()
    {
        barArraylist = new ArrayList();
        barArraylist.add(new BarEntry(2f,10));
        barArraylist.add(new BarEntry(3f,20));
        barArraylist.add(new BarEntry(4f,30));
        barArraylist.add(new BarEntry(5f,40));
        barArraylist.add(new BarEntry(6f,50));
        barArraylist.add(new BarEntry(7f,60));
        barArraylist.add(new BarEntry(8f,70));
        barArraylist.add(new BarEntry(9f,50));
        barArraylist.add(new BarEntry(10f,40));
        barArraylist.add(new BarEntry(11f,50));
        barArraylist.add(new BarEntry(12f,60));
        barArraylist.add(new BarEntry(13f,70));
        barArraylist.add(new BarEntry(14f,10));
        barArraylist.add(new BarEntry(15f,20));
    }
}