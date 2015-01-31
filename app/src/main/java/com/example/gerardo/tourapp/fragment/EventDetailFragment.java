package com.example.gerardo.tourapp.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.tourapp.R;
import com.example.gerardo.tourapp.app.adapter.EventListAdapter;
import com.example.gerardo.tourapp.app.data.ActivityContract;
import com.example.gerardo.tourapp.app.data.ActivityDBHelper;

/**
 * Created by Gerardo on 1/20/2015.
 */
public class EventDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private String title, description, startDate, endDate, budget;
    private SQLiteDatabase db;
    private ActivityDBHelper mDbHelper;
    private ShareActionProvider mShareActionProvider;
    private Bundle extras;

    private TextView titleTextview;
    private TextView descriptionTextView;
    private Button addButton;


    private static final String[] ACTIVITIES_COLUMNS = {
            ActivityContract.Activities.TABLE_NAME + "." + ActivityContract.Activities._ID,
            ActivityContract.Activities._Title,
            ActivityContract.Activities._Description,
            ActivityContract.Activities._StartDate,
            ActivityContract.Activities._EndDate,
            ActivityContract.Activities._Budget
    };

    private static final int EVENT_LOADER = 0;

    public EventDetailFragment() {

         setHasOptionsMenu(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        String sortOrder = ActivityContract.Activities._ID + " ASC";

        return new CursorLoader(
                getActivity(),
                ActivityContract.Activities.CONTENT_URI,
                ACTIVITIES_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data!=null && data.moveToFirst())
        {
            title = extras.getString(ActivityContract.Activities._Title);
            titleTextview.setText(title);


            description = extras.getString(ActivityContract.Activities._Description);
            descriptionTextView.setText(description);



            startDate = extras.getString(ActivityContract.Activities._StartDate);
            endDate = extras.getString(ActivityContract.Activities._EndDate);
            budget = extras.getString(ActivityContract.Activities._Budget);


            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    mDbHelper = new ActivityDBHelper(getActivity());
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

                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        extras = getArguments();

        if(extras != null && extras.containsKey(ActivityContract.Activities._Title))
        {
            getLoaderManager().initLoader(EVENT_LOADER, null, this);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if(extras != null && extras.containsKey(ActivityContract.Activities._Title))
        {
            getLoaderManager().initLoader(EVENT_LOADER, null, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        titleTextview = (TextView) rootView.findViewById(R.id.title_TextView);
        descriptionTextView = (TextView) rootView.findViewById(R.id.description_TextView);


        addButton = (Button) rootView.findViewById(R.id.addButton);



        // Gets the data repository in write mode

        return rootView;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.share_action_menu, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if(title!=null) {
            mShareActionProvider.setShareIntent(createShareEventIntent());
        }
    }

    private Intent createShareEventIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I'm going to " + title + " on " + startDate + "!");
        return shareIntent;
    }
}
