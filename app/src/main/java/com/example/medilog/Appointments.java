package com.example.medilog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Appointments extends AppCompatActivity implements
        View.OnClickListener {

    Button btnTimePicker;
    EditText txtTime, txtDate;
    CalendarView datePicker;
    private int mHour, mMinute, mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        getSupportActionBar().setTitle("Appointments");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        btnTimePicker = (Button) findViewById(R.id.pick_time_button);
        txtTime = (EditText) findViewById(R.id.in_time);
        datePicker = (CalendarView) findViewById(R.id.calendarView);
        txtDate = (EditText) findViewById(R.id.in_date);

        btnTimePicker.setOnClickListener(this);
        datePicker.setOnClickListener(this);

        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                txtDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }

        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        Intent myIntent = new Intent(Appointments.this, Profile.class);
        startActivity(myIntent);
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        if (view == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            if (hourOfDay < 10 && minute < 10) {
                                txtTime.setText("0" + hourOfDay + ":" + "0" + minute);
                            } else if (hourOfDay < 10) {
                                txtTime.setText("0" + hourOfDay + ":" + minute);
                            } else if (minute < 10) {
                                txtTime.setText(hourOfDay + ":" + "0" + minute);
                            } else {
                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}

