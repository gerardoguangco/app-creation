package com.example.gerardo.tourapp.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.gerardo.tourapp.R;
import com.example.gerardo.tourapp.app.DetailActivity;
import com.example.gerardo.tourapp.app.adapter.EventListAdapter;
//import com.example.gerardo.tourapp.fragment.EventListFragment.DetailActivity;
import com.example.gerardo.tourapp.app.data.ActivityContract;


/**
 * Encapsulates fetching the forecast and displaying it as a {@link android.widget.ListView} layout.
 */

public class EventListFragment extends Fragment implements LoaderCallbacks<Cursor> {

    private static final int FORECAST_LOADER = 0;

    private EventListAdapter mActivityAdapter;
    private String activityTitle="";
    ListView listView;


    private static final String[] ACTIVITY_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            ActivityContract.Activities.TABLE_NAME + "." + ActivityContract.Activities._ID,
            ActivityContract.Activities._Title,
            ActivityContract.Activities._Description,
            ActivityContract.Activities._StartDate,
            ActivityContract.Activities._EndDate,
            ActivityContract.Activities._Budget
    };


    public static final int ACTIVITY_ID = 0;
    public static final int ACTIVITY_TITLE = 1;
    public static final int ACTIVITY_DESCRIPTION = 2;
    public static final int ACTIVITY_STARTDATE = 3;
    public static final int ACTIVITY_ENDDATE = 4;
    public static final int ACTIVITY_BUDGET = 5;

    private String startDay;
    private String endDay;
    private int budget;


    public EventListFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getActivity().getIntent();
        Bundle extras = new Bundle();
        extras = intent.getExtras();
        startDay = extras.getString("startDate");
        endDay = extras.getString("endDate");
        budget = extras.getInt("budget");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclable_list_view, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_view);

        mActivityAdapter = new EventListAdapter(getActivity(), null, 0);

        activityTitle = getActivity().getTitle().toString();


        listView.setAdapter(mActivityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = mActivityAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    String title, description, startDate, endDate, budgetString;

                    title = cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._Title));
                    description = cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._Description));
                    startDate = cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._StartDate));
                    endDate = cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._EndDate));
                    budgetString = cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._Budget));



                    Intent intent = new Intent(getActivity(), DetailActivity.class);

                    Bundle extras = new Bundle();
                    extras.putString("title", title);
                    extras.putString("description", description);

                    extras.putString("startDate", startDate);
                    extras.putString("endDate", endDate);
                    extras.putString("budget", budgetString);
                    intent.putExtras(extras);

                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {



        //where columnDate between '2012-07-01' and '2012-07-07'

        String[] SELECTION_ARGS = {Integer.toString(budget)};
        String sortOrder = ActivityContract.Activities._ID + " ASC";
        String selection;

        if(budget==0)
        {

            selection = ActivityContract.Activities._StartDate + " between " + "'" + startDay + "'" + " and " +"'" +endDay+"'";

            return new CursorLoader(
                    getActivity(),
                    ActivityContract.Activities.CONTENT_URI,
                    ACTIVITY_COLUMNS,
                    selection,
                    null,
                    sortOrder
            );
        }
        else
        {
            selection = ActivityContract.Activities._StartDate + " between " + "'" + startDay + "'" + " and " +"'" +endDay+"'"  + " AND " + ActivityContract.Activities._Budget + "<=?";
            return new CursorLoader(
                    getActivity(),
                    ActivityContract.Activities.CONTENT_URI,
                    ACTIVITY_COLUMNS,
                    selection,
                    SELECTION_ARGS,
                    sortOrder
            );
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mActivityAdapter.swapCursor(data);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(FORECAST_LOADER, null, this);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mActivityAdapter.swapCursor(null);
    }

}