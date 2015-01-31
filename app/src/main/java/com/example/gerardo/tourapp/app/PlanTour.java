package com.example.gerardo.tourapp.app;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerardo.tourapp.R;
import com.example.gerardo.tourapp.app.data.ActivityContract;
import com.example.gerardo.tourapp.app.data.ActivityContract.Activities;
import com.example.gerardo.tourapp.fragment.SlidePanel;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;


public class PlanTour extends ActionBarActivity {

    Context mContext;
    private Toolbar toolbar;

    public void PlanTour(Context context){
        mContext = context;
    }

    Calendar myCalendar = Calendar.getInstance();
    Date fromDate = new Date();
    Date toDate = new Date();
    int numberOfDays;

    private SimpleDateFormat sdf;
    private String startDay, endDay;
    private String fromDateString;
    private String toDateString;
    private int budget;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_tour);

        final EditText fromDateText = (EditText)findViewById(R.id.from);
        final EditText toDateText = (EditText)findViewById(R.id.to);
        final EditText budgetText = (EditText)findViewById(R.id.budgetText);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SlidePanel slidePanel = (SlidePanel) getSupportFragmentManager().findFragmentById(R.id.fragment_slide_panel);
        slidePanel.setUp(R.id.fragment_slide_panel,(DrawerLayout)findViewById(R.id.drawerLayout), toolbar);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                fromDateString = fromDateText.getText().toString();
                toDateString = toDateText.getText().toString();

                if(budgetText.getText().toString().matches(""))
                {
                    budget = 0;
                }
                else {
                    budget = Integer.parseInt(budgetText.getText().toString());
                }


                if(fromDateString.matches("")||toDateString.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please input a Date of Visit", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    startDay = fromDateText.getText().toString();
                    endDay = toDateText.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), ListItemActivity.class);
                    intent.putExtra("startDate", startDay);
                    intent.putExtra("endDate",endDay);
                    intent.putExtra("budget", budget);
                    startActivity(intent);
                }

            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                fromDate = new Date(year,monthOfYear,dayOfMonth);
                updateLabelFromDate();

            }
        };

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                toDate = new Date(year,monthOfYear,dayOfMonth);
                updateLabelToDate();
            }
        };

        fromDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PlanTour.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        toDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PlanTour.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


    public void updateLabelFromDate(){

        EditText fromDateText = (EditText)findViewById(R.id.from);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        fromDateText.setText(sdf.format(myCalendar.getTime()));
    }

    public void updateLabelToDate(){

        EditText toDateText = (EditText)findViewById(R.id.to);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        toDateText.setText(sdf.format(myCalendar.getTime()));
    }

//    public void calculateDays(){
//
//        numberOfDays = Days.daysBetween(new DateTime(fromDate), new DateTime(toDate)).getDays();
//        Toast.makeText(getApplicationContext(), numberOfDays+" number of days", Toast.LENGTH_SHORT).show();
//
//    }

}
