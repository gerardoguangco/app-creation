package com.example.gerardo.tourapp.app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.tourapp.R;
import com.example.gerardo.tourapp.app.data.ActivityContract;
import com.example.gerardo.tourapp.app.data.ActivityDBHelper;
import com.example.gerardo.tourapp.fragment.EventDetailFragment;
import com.example.gerardo.tourapp.fragment.EventListFragment;

/**
 * Created by Gerardo on 1/31/2015.
 */
public class DetailActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ShareActionProvider mShareActionProvider;
    private SQLiteDatabase db;
    private ActivityDBHelper mDbHelper;
    private String title, description, startDate, endDate, budget;

    private TextView titleTextview;
    private TextView descriptionTextView;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_from_event_list);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        insertData();
    }

    public void insertData()
    {
        titleTextview = (TextView)findViewById(R.id.title_TextView);
        descriptionTextView = (TextView)findViewById(R.id.description_TextView);
        addButton = (Button)findViewById(R.id.addButton);


        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");
        budget = intent.getStringExtra("budget");

        titleTextview.setText(title);
        descriptionTextView.setText(description);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mDbHelper = new ActivityDBHelper(getApplicationContext());
                db = mDbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(ActivityContract.UserSchedule._userActivityID, ActivityContract.Users._ID);
                values.put(ActivityContract.UserSchedule._Title, title);
                values.put(ActivityContract.UserSchedule._Description, description);
                values.put(ActivityContract.UserSchedule._StartDate, startDate);
                values.put(ActivityContract.UserSchedule._EndDate, endDate);
                values.put(ActivityContract.UserSchedule._Budget, budget);

                long newRowId;
                newRowId = db.insert(
                        ActivityContract.UserSchedule.TABLE_NAME,
                        ActivityContract.UserSchedule._ID,
                        values);

                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
}
