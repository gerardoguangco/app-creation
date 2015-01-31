package com.example.gerardo.tourapp.app;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.gerardo.tourapp.app.data.ActivityContract;

import java.util.Vector;

/**
 * Created by Gerardo on 1/29/2015.
 */
public class initializeDummyData extends AsyncTask<String, Void, Void> {


    private final Context mContext;

    public initializeDummyData(Context context) {
        mContext = context;

    }

    @Override
    protected Void doInBackground(String... params) {

            String [] title = {
                    "Sinulog",
                    "Kaamulan",
                    "Higalaay",
                    "Ati-Atihan",
                    "Dinagyang",
                    "Higantes",
                    "Kadayawan",
                    "Pagoda",
                    "Buklog",
                    "Flores de Mayo"

            };

            String [] description = {
                    "sinulog 2015 is fun! This event is one of the most awaited and participated festival in the Philippines. This event is one of the most awaited and participated festival in the Philippines",
                    "kaamulan 2015 is cool! kaamulan 2015 is cool! kaamulan 2015 is cool! kaamulan 2015 is cool!",
                    "higalaay 2015 is awesome!",
                    "atiatihan 2015 is full of colors!",
                    "dinagyang 2015 is good! dinagyang 2015 is good! dinagyang 2015 is good! dinagyang 2015 is good! dinagyang 2015 is good! dinagyang 2015 is good!",
                    "higantes 2015 is worth it!",
                    "kadayawan 2015 is amazing!",
                    "pagoda 2015 is coming!",
                    "buklog 2015 is igniting!",
                    "flores de mayo 2015 is full of flowers!"
            };

            String [] startDate = {
                    "2015-01-01",
                    "2015-01-02",
                    "2015-01-03",
                    "2015-01-04",
                    "2015-01-05",
                    "2015-01-06",
                    "2015-01-07",
                    "2015-01-08",
                    "2015-01-09",
                    "2015-01-10"
            };

            String [] endDate = {
                    "Feb 1, 2015",
                    "Feb 2, 2015",
                    "Feb 3, 2015",
                    "Feb 4, 2015",
                    "Feb 5, 2015",
                    "Feb 6, 2015",
                    "Feb 7, 2015",
                    "Feb 8, 2015",
                    "Feb 9, 2015",
                    "Feb 10, 2015"
            };

            int [] budget = {
                    100,
                    200,
                    300,
                    400,
                    500,
                    600,
                    700,
                    800,
                    900,
                    1000
            };

            Vector<ContentValues> cVVector = new Vector<ContentValues>(title.length);


            for(int counter=0, id=1; title.length>counter; counter++, id++){


                ContentValues activityValues = new ContentValues();
                activityValues.put(ActivityContract.Activities._Title, title[counter]);
                activityValues.put(ActivityContract.Activities._Description, description[counter]);
                activityValues.put(ActivityContract.Activities._StartDate, startDate[counter]);
                activityValues.put(ActivityContract.Activities._EndDate, endDate[counter]);
                activityValues.put(ActivityContract.Activities._Budget, budget[counter]);
                cVVector.add(activityValues);
            }

            if (cVVector.size() > 0) {

                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                mContext.getContentResolver().bulkInsert(ActivityContract.Activities.CONTENT_URI, cvArray);

            }

        return null;
    }
}
