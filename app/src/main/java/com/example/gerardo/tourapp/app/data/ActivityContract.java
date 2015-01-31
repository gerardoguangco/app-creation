package com.example.gerardo.tourapp.app.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gerardo on 12/31/2014.
 */
public class ActivityContract {

    public static final String CONTENT_AUTHORITY = "com.example.gerardo.googletours.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.

    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.

    public static final String PATH_USER = "users";
    public static final String PATH_ACTIVITIES = "activities";
    public static final String PATH_USERACTIVITIES = "user_activities";



    public static final class Users implements BaseColumns {

        public static final String TABLE_NAME = "users";

        public static final String _firstName = "user_first_name";
        public static final String _lastName = "user_last_name";
        public static final String _password = "user_password";
        public static final String _emailAddress = "user_email";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();


    }


        public static final class Activities implements BaseColumns {


            public static final String TABLE_NAME = "activities";

            public static final String _Title = "activity_title";
            public static final String _Description = "activity_description";
            public static final String _StartDate = "activity_start_date";
            public static final String _EndDate = "activity_end_date";
            public static final String _Budget = "activity_budget";

            public static final Uri CONTENT_URI =
                    BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTIVITIES).build();



        }

        public static final class UserSchedule implements BaseColumns {

            public static final String TABLE_NAME = "user_activities";

            public static final String _userActivityID = "user_id";
            public static final String _Title = "activity_title";
            public static final String _Description = "activity_description";
            public static final String _StartDate = "activity_start_date";
            public static final String _EndDate = "activity_end_date";
            public static final String _Budget = "activity_budget";

            public static final Uri CONTENT_URI =
                    BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERACTIVITIES).build();

            public static final String CONTENT_TYPE =
                    "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_USERACTIVITIES;
            public static final String CONTENT_ITEM_TYPE =
                    "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_USERACTIVITIES;


            public static Uri buildUserScheduleUri(long id) {
                return ContentUris.withAppendedId(CONTENT_URI, id);
            }

        }

    public static final class WeatherTemperature implements BaseColumns {

        public static final String TABLE_NAME = "weather_temp";

        public static final String _Temperature = "weather temperature";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERACTIVITIES).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_USERACTIVITIES;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_USERACTIVITIES;


        public static Uri buildUserScheduleUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
