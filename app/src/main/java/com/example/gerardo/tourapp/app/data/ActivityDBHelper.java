package com.example.gerardo.tourapp.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gerardo.tourapp.app.data.ActivityContract.Activities;
import com.example.gerardo.tourapp.app.data.ActivityContract.UserSchedule;
import com.example.gerardo.tourapp.app.data.ActivityContract.Users;

/**
 * Created by Gerardo on 12/31/2014.
 */

public class ActivityDBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "googleTours.db";
    public static final int DB_VERSION = 2;


    public ActivityDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + Users.TABLE_NAME + " (" +
                Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Users._firstName + " TEXT NOT NULL, " +
                Users._lastName + " TEXT NOT NULL, " +
                Users._emailAddress + " TEXT UNIQUE NOT NULL, " +
                Users._password + " TEXT UNIQUE NOT NULL"+
                ");";



        final String SQL_CREATE_ACTIVITIES_TABLE = "CREATE TABLE " + Activities.TABLE_NAME + " (" +

                Activities._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                Activities._Title + " TEXT NOT NULL, " +
                Activities._Description + " TEXT NOT NULL, " +
                Activities._StartDate + " DATE NOT NULL, " +
                Activities._EndDate + " TEXT NOT NULL," +
                Activities._Budget + " INT NOT NULL" +
                " );";

        final String SQL_CREATE_USERS_SCHEDULE_TABLE = "CREATE TABLE " + UserSchedule.TABLE_NAME + " (" +

                UserSchedule._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                UserSchedule._userActivityID + " INTEGER NOT NULL," +
                UserSchedule._Title + " TEXT NOT NULL, " +
                UserSchedule._Description + " TEXT NOT NULL, " +
                UserSchedule._StartDate + " TEXT NOT NULL, " +
                UserSchedule._EndDate + " TEXT NOT NULL," +
                UserSchedule._Budget + " INT NOT NULL," +

                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + UserSchedule._userActivityID + ") REFERENCES " +
                Users.TABLE_NAME + " (" + Users._ID + ") " +

                ");";


        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ACTIVITIES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USERS_SCHEDULE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Users.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Activities.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserSchedule.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Users.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Activities.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserSchedule.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
