package com.example.gerardo.tourapp.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gerardo.tourapp.R;
import com.example.gerardo.tourapp.app.AboutActivity;
import com.example.gerardo.tourapp.app.FindHotel;
import com.example.gerardo.tourapp.app.MainActivity;
import com.example.gerardo.tourapp.app.PlanTour;
import com.example.gerardo.tourapp.app.UserProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SlidePanel extends Fragment {


    public static final String PREF_FILE_NAME = "testpref";             //Serves as filename for users preferences.
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";     //Serves as the variable to know if the users has already discovered the drawer
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private ArrayAdapter<String> forecastAdapter;

    private View containerView;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;


    public SlidePanel() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer=Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] dummyData = {
                "Home",
                "Profile",
                "Plan Tour",
                "Find Hotel",
                "About"
        };
        List<String> navigationList = new ArrayList<String>(Arrays.asList(dummyData));

        String temp = "fragment_list_view_detail";

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.recyclable_list_view, container, false);
        forecastAdapter = new ArrayAdapter<String>(getActivity(),R.layout.recyclable_list_view_detail,R.id.wew_textview, navigationList);

        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(forecastAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                navigatePage(position);
            }
        });


        return rootView;
    }

    public void navigatePage(int position)
    {

        Intent intent = null;
        Class<?> c = null;

        if(position==0)
        {
            intent = new Intent(getActivity(), MainActivity.class);
        }
        if(position==1)
        {
            intent = new Intent(getActivity(), UserProfile.class);
        }
        if(position==2)
        {
            intent = new Intent(getActivity(), PlanTour.class);
        }
        if(position==3)
        {
            intent = new Intent(getActivity(), FindHotel.class);
        }
        if(position==4)
        {
            intent = new Intent(getActivity(), AboutActivity.class);
        }

        startActivity(intent);

    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout=drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close)
        {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu(); //
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

        };
        if(!mUserLearnedDrawer&&!mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable(){
            @Override
            public void run(){
                mDrawerToggle.syncState();
            }
        });

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    //Saves the users preferennce
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();

    }

    //Reads the users preferences.
    public static String readFromPreferences(Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }


}
