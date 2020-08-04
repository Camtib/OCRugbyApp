package com.example.ocrugbyapp.members;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ocrugbyapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MembersListAdapter extends ArrayAdapter<MembersCard> {

    private static final String TAG = "MemberslistAdapter";
    private Context mContext;
    private int mResource;

    public MembersListAdapter(@NonNull Context context, int resource, @NonNull List<MembersCard> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    private static class ViewHolder {
        TextView name;
        TextView nickname;
        ImageView profilePic;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get fixture information
        String name = getItem(position).getName();
        String nickname = getItem(position).getNickname();
        Uri profilePic = getItem(position).getProfileUri();


        //create the fixture object with the information
        MembersCard membersCard = new MembersCard(name, nickname, profilePic);

        //create view result to show animation
        final View result;

        //ViewHolder object
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textViewName);
            holder.nickname = (TextView) convertView.findViewById(R.id.textViewNickname);
            holder.profilePic = (ImageView) convertView.findViewById(R.id.profilePics);


            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }



        holder.name.setText(name);
        holder.nickname.setText(nickname);
        if (profilePic != null) {
            Picasso.get().load(profilePic).into(holder.profilePic);
        }


        return convertView;

    }
}

