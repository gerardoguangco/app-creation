package com.example.gerardo.tourapp.app.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerardo.tourapp.R;
import com.example.gerardo.tourapp.fragment.EventListFragment;
import com.example.gerardo.tourapp.fragment.UserEventListFragment;

/**
 * {@link EventListAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
 */
public class UserEventListAdapter extends CursorAdapter {


    public UserEventListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        int layoutId = -1;

        layoutId = R.layout.activity_event_list_details;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    public static class ViewHolder{
        public final ImageView iconView;
        public final TextView title_textView;
        public final TextView description_textView;
        public final TextView startDate_textView;
        public final TextView budget_textView;

        public ViewHolder (View view){
            iconView = (ImageView) view.findViewById(R.id.imageView2);
            title_textView = (TextView) view.findViewById(R.id.title_textView);
            description_textView = (TextView) view.findViewById(R.id.description_textView);
            startDate_textView = (TextView) view.findViewById(R.id.startDate_textView);
            budget_textView = (TextView) view.findViewById(R.id.budgetTextView);

        }

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int activityId = cursor.getInt(EventListFragment.ACTIVITY_ID);
        viewHolder.iconView.setImageResource(R.drawable.dot);

        String titleString = cursor.getString(EventListFragment.ACTIVITY_TITLE);
        viewHolder.title_textView.setText(titleString);

        String descriptionString = cursor.getString(EventListFragment.ACTIVITY_DESCRIPTION);
        viewHolder.description_textView.setText(descriptionString);

        String startdateString = cursor.getString(EventListFragment.ACTIVITY_STARTDATE);
        viewHolder.startDate_textView.setText(startdateString);

        String budget = Integer.toString(cursor.getInt(EventListFragment.ACTIVITY_BUDGET));
        viewHolder.budget_textView.setText(budget);


    }
}