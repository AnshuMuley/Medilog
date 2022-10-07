package com.example.medilog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Analysis extends AppCompatActivity {
    Spinner spinner;
    EditText editText;
    Button button;
    TextView textView;


    //private static final String[] Tests = {"Fasting" , "Posting" ,"Random" ,"A1c Test"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        getSupportActionBar().setTitle("Analysis");


       /* button = (Button) findViewById(R.id.button8);
        spinner = (Spinner) findViewById(R.id.spinner2);*/

        Spinner spinnerTest = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Analysis.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Tests));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTest.setAdapter(adapter);

        //spinnerTest.setOnItemSelectedListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        button = (Button) findViewById(R.id.button8);
        // editText = (EditText) findViewById(R.id.editTextNumber);
        textView = (TextView) findViewById(R.id.textView4);
        spinner = (Spinner) findViewById(R.id.spinner2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            EditText Range= (EditText) findViewById(R.id.editTextNumberDecimal);
                String regex = "^\\d*\\.\\d+|\\d+\\.\\d*$";
                String test=spinner.getSelectedItem().toString();
                String s=Range.getText().toString();
                Double range=Double.parseDouble(s);
                if(test.equals("Fasting")) {
                    if (range >= 70 && range <= 100) {
                        textView.setText("Indicates an optimal report . Blood sugar level is balanced in blood.");
                    }
                    else if(range<=70){
                        textView.setText("Indicates Low sugar level.");

                    }
                    else if(range>=100 && range<=125)
                    {
                        textView.setText("Non Diabetic person - Slightly Higher Sugar level . Indicates a person is Pre Diabetic.\n"+
                                "\n**Condition - This may happen suddenly during a major illness or injury or may happen over a longer period "+
                                        "and be caused by a chronic disease (Diabetes I or II.)**\n"+
                                "\nDiabetic person - Indicates low sugar level that means the medications are lowering the blood "+
                                " sugar level too much or over production of insulin.");
                    }
                    else if(range>=126) {
                       // textView.setText("Indicates a person is Diabetic");
                        if (126 <= range && range <= 180) {
                            textView.setText("Indicates an optimal report for a Diabetic person.\nPrescribed Medication should be continued. \n " +
                                    "Precautions must be taken for eating habits");

                        } else if (range > 180) {
                            textView.setText("High levels of fasting blood sugar suggest the body was not able to lower blood sugar levels\n" +
                                    " this points to insulin resistance , inadequate insulin production , or in some cases, both .\n" +
                                    " * Must consult to a specialist. *");
                        }
                    }
                }

                if(test.equals("Posting")){
                   if(range<=70){
                        textView.setText("Indicates Low sugar level.");
                    }
                   else if(range>=71 && range<=140){
                       textView.setText("Indicates an optimal Post Meal report .");
                   }
                   else if(range>=140 && range<=199){
                       textView.setText("Diabetic  -  It indicates an optimal report for a diabetic person .\n"+
                               "\nNon Diabetic - A person may be Pre-Diabetic\n"+
                               "\n* Consulting a specialist is recommended. *");
                   }
                   else if(range>=200){
                       textView.setText("Indicates a person is Diabetic.\n"+
                               "Condition -  This may arise from under production of Insulin.\n"+
                               "High intake of carbohydrates or Glucose .\n"+
                               "\n * Consult a specialist and get your medicines revised."+
                               "\nPrecautions must be taken in your eating habits. *");
                   }
                }
                if(test.equals("Random")){

                    if(range<=70){
                        textView.setText("Indicates Low sugar level.\n"+"Immediate intake of juice, candy, glucose is highly recommended. ");
                    }
                    else if(range>=71 && range<=139)
                    {
                        textView.setText("Indicates an optimal Random report .");
                    }
                    else if(range>=140 && range<=199)
                    {
                        textView.setText(" Indicates a person is Pre-Diabetic. \n"+"Consulting a specialist is recommended.\n"+"Generally Doctors recommend this test to diagnose Diabetes.\n"+"To Help confirm the Diagnosis,the doctor may also order different type of test. " );
                    }
                    else if(range>=200)
                    {
                        textView.setText("Indicates a person is Diabetic.\n"+"Consulting a specialist is recommended.\n"+"Generally Doctors recommend this test to diagnose Diabetes.\n"+"To Help confirm the Diagnosis,the doctor may also order different type of test. " );
                    }
                }
                if(test.equals("A1c Test"))
                {
                 //   if (s.getText().toString().trim().matches(regex)) {
                       // test.setError("Invalid First Name");
                        //isAllFieldsChecked =false;

                        if (range <= 5.7) {
                            textView.setText("Indicates an optimal A1c test report .");
                        } else if (range > 5.7 && range <= 6.4) {
                            textView.setText(" Indicates a person is Pre-Diabetic. \n" + "Consulting a specialist is recommended.");
                        } else if (range >= 6.5) {
                            textView.setText(" Indicates a person is Diabetic. \n" + "Consulting a specialist is recommended.");
                        }
                  //  }
                }
            }
        });
     /*   ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/




    }



    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        Intent myIntent = new Intent(Analysis.this, Profile.class);
        startActivity(myIntent);
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}