package com.example.gerardo.tourapp.app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.gerardo.tourapp.R;
import com.example.gerardo.tourapp.app.data.ActivityContract;
import com.example.gerardo.tourapp.app.data.ActivityDBHelper;


public class UserProfile extends ActionBarActivity {

    private Toolbar toolbar;
    private String firstName;
    private String lastName;
    private String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActivityDBHelper mDbHelper = new ActivityDBHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                ActivityContract.Users._firstName,
                ActivityContract.Users._lastName,
                ActivityContract.Users._emailAddress,
        };

        Cursor c = db.query(
                ActivityContract.Users.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        c.moveToFirst();
        firstName = c.getString(c.getColumnIndex(ActivityContract.Users._firstName));
        lastName = c.getString(c.getColumnIndex(ActivityContract.Users._lastName));
        emailAddress = c.getString(c.getColumnIndex(ActivityContract.Users._emailAddress));

        View view = findViewById(R.id.container);

        TextView firstNameView = (TextView) view.findViewById(R.id.firstName_textView);
        firstNameView.setText(firstName);

        TextView lastNameView = (TextView) view.findViewById(R.id.lastName_textView);
        lastNameView.setText(lastName);

        TextView emailAddressView = (TextView) view.findViewById(R.id.emailAddressView);
        emailAddressView.setText(emailAddress);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
