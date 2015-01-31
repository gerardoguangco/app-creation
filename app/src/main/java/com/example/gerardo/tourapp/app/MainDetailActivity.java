package com.example.gerardo.tourapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.gerardo.tourapp.R;
import com.example.gerardo.tourapp.app.data.ActivityContract;
import com.example.gerardo.tourapp.fragment.EventDetailFragment;

public class MainDetailActivity extends ActionBarActivity {


    private String text = "I am going to ";
    private Toolbar toolbar;
    private String titleString, descriptionString, startDateString, endDateString, budgetString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {



            titleString = getIntent().getStringExtra(ActivityContract.Activities._Title);
            descriptionString = getIntent().getStringExtra(ActivityContract.Activities._Description);
            startDateString = getIntent().getStringExtra(ActivityContract.Activities._StartDate);
            endDateString = getIntent().getStringExtra(ActivityContract.Activities._EndDate);
            budgetString = getIntent().getStringExtra(ActivityContract.Activities._Budget);


            Bundle extras = new Bundle();

            extras.putString(ActivityContract.Activities._Title, titleString);
            extras.putString(ActivityContract.Activities._Description, descriptionString);

            extras.putString(ActivityContract.Activities._StartDate, startDateString);
            extras.putString(ActivityContract.Activities._EndDate, endDateString);
            extras.putString(ActivityContract.Activities._Budget, budgetString);

            EventDetailFragment frag = new EventDetailFragment();
            frag.setArguments(extras);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.event_list_container, frag)
                    .commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
