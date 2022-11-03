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

        //spinnerTest.setOnItemSelectedListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        button = (Button) findViewById(R.id.button8);
        textView = (TextView) findViewById(R.id.textView4);
        spinner = (Spinner) findViewById(R.id.spinner2);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(Analysis.this,Barchart.class);
                EditText Range= (EditText) findViewById(R.id.editTextNumberDecimal);
                String regex = "^\\d*\\.\\d+|\\d+\\.\\d*$";
                String test=spinner.getSelectedItem().toString();
                String s=Range.getText().toString();
                i3.putExtra("test",test);
                i3.putExtra("range",s);
                startActivity(i3);
                Toast.makeText(Analysis.this, "Moved to Barchart", Toast.LENGTH_SHORT).show();

            }
        });
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