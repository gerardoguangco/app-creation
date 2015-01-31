package com.example.gerardo.tourapp.app;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gerardo.tourapp.R;
import com.example.gerardo.tourapp.app.data.ActivityContract;
import com.example.gerardo.tourapp.app.data.ActivityDBHelper;
import com.example.gerardo.tourapp.fragment.EventDetailFragment;
import com.example.gerardo.tourapp.fragment.MainPageEventListFragment;
import com.example.gerardo.tourapp.fragment.SlidePanel;


public class MainActivity extends ActionBarActivity implements MainPageEventListFragment.Callback{

    private Toolbar toolbar;
    private SQLiteDatabase db;
    private ActivityDBHelper mDbHelper;
    private boolean mTwoPane;

    private static final String[] USER_COLUMNS = {

            ActivityContract.Users.TABLE_NAME + "." + ActivityContract.Users._ID
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SlidePanel slidePanel = (SlidePanel) getSupportFragmentManager().findFragmentById(R.id.fragment_slide_panel);
        slidePanel.setUp(R.id.fragment_slide_panel,(DrawerLayout)findViewById(R.id.drawerLayout), toolbar);


        if (findViewById(R.id.event_list_container) != null) {

            mTwoPane = true;


            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.event_list_container, new EventDetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }


        mDbHelper = new ActivityDBHelper(getApplicationContext());

        Cursor cursor = mDbHelper.getReadableDatabase().query(
                ActivityContract.Users.TABLE_NAME,
                USER_COLUMNS,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.getCount() == 0)
        {
            createUser();
        }
        else
        {
        }


    }

    @Override
    public void onItemSelected(String title, String description, String startDate, String endDate, String budget) {
        if (mTwoPane) {

            Bundle extras = new Bundle();

            extras.putString(ActivityContract.Activities._Title, title);
            extras.putString(ActivityContract.Activities._Description, description);

            extras.putString(ActivityContract.Activities._StartDate, startDate);
            extras.putString(ActivityContract.Activities._EndDate, endDate);
            extras.putString(ActivityContract.Activities._Budget, budget);


            EventDetailFragment fragment = new EventDetailFragment();
            fragment.setArguments(extras);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.event_list_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, MainDetailActivity.class)
                    .putExtra(ActivityContract.Activities._Title, title)
                    .putExtra(ActivityContract.Activities._Description, description)
                    .putExtra(ActivityContract.Activities._StartDate, startDate)
                    .putExtra(ActivityContract.Activities._EndDate, endDate)
                    .putExtra(ActivityContract.Activities._Budget, budget);

            startActivity(intent);
        }
    }


    public void createUser(){

        mDbHelper = new ActivityDBHelper(getApplicationContext());
        // Gets the data repository in write mode
        db = mDbHelper.getWritableDatabase();

        String userFirstName = "Gerardo";
        String userLastName = "Guangco";
        String userPassword = "password";
        String userEmail = "gerardoguangco@gmail.com";


        ContentValues values = new ContentValues();
        values.put(ActivityContract.Users._firstName, userFirstName);
        values.put(ActivityContract.Users._lastName, userLastName);
        values.put(ActivityContract.Users._password, userPassword);
        values.put(ActivityContract.Users._emailAddress, userEmail);

        long newRowId;
        newRowId = db.insert(
                ActivityContract.Users.TABLE_NAME,
                ActivityContract.Users._ID,
                values);

        //creating dummy data
        new initializeDummyData(getApplicationContext()).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
