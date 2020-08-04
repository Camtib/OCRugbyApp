package com.example.ocrugbyapp.fixtures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.ocrugbyapp.R;

import java.util.ArrayList;

public class WomensFixturesListAdapter extends ArrayAdapter<WomensFixtureCard> {

    private static final String TAG = "WomensFixtureListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    public WomensFixturesListAdapter(@NonNull FragmentActivity context, int resource, @NonNull ArrayList<WomensFixtureCard> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    //holds variables in a view
    private static class ViewHolder {
        TextView date;
        TextView womensFixture;
        TextView womensKOTime;
        TextView womensHA;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get fixture information
        String date = getItem(position).getDate();
        String womensFixture = getItem(position).getWomensFixture();
        String womensKOTime = getItem(position).getWomensKOTime();
        String womensHA = getItem(position).getWomensHA();

        //create the fixture object with the information
        WomensFixtureCard fixtures = new WomensFixtureCard(date, womensFixture,  womensKOTime,  womensHA);

        //create view result to show animation
        final View result;

        //ViewHolder object
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.dateTextView);
            holder.womensFixture = (TextView) convertView.findViewById(R.id.womensFixture);
            holder.womensKOTime = (TextView) convertView.findViewById(R.id.womensKOTime);
            holder.womensHA = (TextView) convertView.findViewById(R.id.womensHA);


            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.loading_down_anim : R.anim.loading_up_anim);
        result.startAnimation(animation);
        lastPosition = position;


        holder.date.setText(date);
        holder.womensFixture.setText(womensFixture);
        holder.womensKOTime.setText(womensKOTime);
        holder.womensHA.setText(womensHA);


        return convertView;

    }
}
