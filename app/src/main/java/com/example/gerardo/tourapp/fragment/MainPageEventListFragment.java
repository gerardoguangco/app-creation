package com.example.gerardo.tourapp.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gerardo.tourapp.R;
import com.example.gerardo.tourapp.app.adapter.MainPageEventListAdapter;
import com.example.gerardo.tourapp.app.data.ActivityContract;

/**
 * Created by Gerardo on 1/29/2015.
 */
public class MainPageEventListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int FORECAST_LOADER = 0;

    private MainPageEventListAdapter mActivityAdapter;
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


    public MainPageEventListFragment() {

    }


    public interface Callback
    {
        public void onItemSelected(String title, String description, String startDate, String endDate, String budget);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View header = inflater.inflate(R.layout.main_activity_list_view_header, null);
        View rootView = inflater.inflate(R.layout.event_list_view, container, false);
        listView = (ListView) rootView.findViewById(R.id.activity_listView);

        mActivityAdapter = new MainPageEventListAdapter(getActivity(), null, 0);

        listView.addHeaderView(header);
        listView.setAdapter(mActivityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                position = position-1;
                Cursor cursor = mActivityAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {

                    ((Callback)getActivity()).
                            onItemSelected(
                                    cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._Title)),
                                    cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._Description)),
                                    cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._StartDate)),
                                    cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._EndDate)),
                                    cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._Budget))
                            );


//                    String title, description, startDate, endDate;
//
//                    title = cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._Title));
//                    description = cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._Description));
//                    startDate = cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._StartDate));
//                    endDate = cursor.getString(cursor.getColumnIndex(ActivityContract.Activities._EndDate));
//
//                    Intent intent = new Intent(getActivity(), MainDetailActivity.class)
//                            .putExtra(ActivityContract.Activities._Title, title)
//                            .putExtra(ActivityContract.Activities._Description, description)
//                            .putExtra(ActivityContract.Activities._StartDate, startDate)
//                            .putExtra(ActivityContract.Activities._EndDate, endDate);
//
////                    Bundle extras = new Bundle();
////
////                    extras.putString(ActivityContract.Activities._Title, title);
////                    extras.putString(ActivityContract.Activities._Description, description);
////
////                    extras.putString(ActivityContract.Activities._StartDate, startDate);
////                    extras.putString(ActivityContract.Activities._EndDate, endDate);
////                    intent.putExtras(extras);
//
//                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder = ActivityContract.Activities._ID + " ASC";
        //where A.Date >= Convert(datetime, '2010-04-01' )
        return new CursorLoader(
                getActivity(),
                ActivityContract.Activities.CONTENT_URI,
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
