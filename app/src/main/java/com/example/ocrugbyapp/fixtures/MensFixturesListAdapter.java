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


public class MensFixturesListAdapter extends ArrayAdapter<MensFixtureCard> {

    private static final String TAG = "MensFixtureListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    public MensFixturesListAdapter(@NonNull FragmentActivity context, int resource, @NonNull ArrayList<MensFixtureCard> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    //holds variables in a view
    private static class ViewHolder {
        TextView date;
        TextView firstsFixture;
        TextView secondsFixture;
        TextView bsFixture;
        TextView firstsHA;
        TextView secondsHA;
        TextView bsHA;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get fixture information
        String date = getItem(position).getDate();
        String firstsFixture = getItem(position).getFirstsFixture();
        String secondsFixture = getItem(position).getSecondsFixture();
        String bsFixture = getItem(position).getBsFixture();
        String firstsHA = getItem(position).getFirstsHA();
        String secondsHA = getItem(position).getSecondsHA();
        String bsHA = getItem(position).getBsHA();

        //create the fixture object with the information
        MensFixtureCard fixture = new MensFixtureCard(date, firstsFixture, secondsFixture, bsFixture, firstsHA, secondsHA, bsHA);

        //create view result to show animation
        final View result;

        //ViewHolder object
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.dateTextView);
            holder.firstsFixture = (TextView) convertView.findViewById(R.id.firstsFixture);
            holder.secondsFixture = (TextView) convertView.findViewById(R.id.secondsFixture);
            holder.bsFixture = (TextView) convertView.findViewById(R.id.bsFixture);
            holder.firstsHA = (TextView) convertView.findViewById(R.id.firstsHA);
            holder.secondsHA = (TextView) convertView.findViewById(R.id.secondsHA);
            holder.bsHA = (TextView) convertView.findViewById(R.id.bsHA);

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
        holder.firstsFixture.setText(firstsFixture);
        holder.secondsFixture.setText(secondsFixture);
        holder.bsFixture.setText(bsFixture);
        holder.firstsHA.setText(firstsHA);
        holder.secondsHA.setText(secondsHA);
        holder.bsHA.setText(bsHA);

        return convertView;

    }
}
