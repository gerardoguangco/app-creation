package com.example.gerardo.tourapp.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.gerardo.tourapp.R;
//import com.example.gerardo.tourapp.fragment.EventListFragment.DetailActivity;
import com.example.gerardo.tourapp.app.adapter.UserEventListAdapter;
import com.example.gerardo.tourapp.app.data.ActivityContract;
import com.example.gerardo.tourapp.app.data.ActivityDBHelper;


/**
 * Encapsulates fetching the forecast and displaying it as a {@link android.widget.ListView} layout.
 */

public class UserEventListFragment extends Fragment implements LoaderCallbacks<Cursor> {

    private static final int FORECAST_LOADER = 0;

    private ActivityDBHelper mDbHelper;
    private SQLiteDatabase db;
    private UserEventListAdapter mActivityAdapter;
    ListView listView;


    private static final String[] ACTIVITY_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            ActivityContract.UserSchedule.TABLE_NAME + "." + ActivityContract.UserSchedule._ID,
            ActivityContract.UserSchedule._Title,
            ActivityContract.UserSchedule._Description,
            ActivityContract.UserSchedule._StartDate,
            ActivityContract.UserSchedule._EndDate,
            ActivityContract.UserSchedule._Budget
    };


    public static final int USER_ACTIVITY_ID = 0;
    public static final int USER_ACTIVITY_TITLE = 1;
    public static final int USER_ACTIVITY_DESCRIPTION = 2;
    public static final int USER_ACTIVITY_STARTDATE = 3;
    public static final int USER_ACTIVITY_ENDDATE = 4;

    public UserEventListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.event_list_view, container, false);
        listView = (ListView) rootView.findViewById(R.id.activity_listView);

        mActivityAdapter = new UserEventListAdapter(getActivity(), null, 0);

        listView.setAdapter(mActivityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = mActivityAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder = ActivityContract.UserSchedule._ID + " ASC";

        return new CursorLoader(
                getActivity(),
                ActivityContract.UserSchedule.CONTENT_URI,
                ACTIVITY_COLUMNS,
                null,
                null,
                sortOrder
        );


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