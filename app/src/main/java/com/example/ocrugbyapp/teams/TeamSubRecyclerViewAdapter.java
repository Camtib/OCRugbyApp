package com.example.ocrugbyapp.teams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ocrugbyapp.R;

import java.util.List;

public class TeamSubRecyclerViewAdapter extends RecyclerView.Adapter<TeamSubRecyclerViewAdapter.SubVH> {

    private Context mContext;
    List<SubPlayer> subList;

    public TeamSubRecyclerViewAdapter(Context mContext, List<SubPlayer> subList) {
        this.mContext = mContext;
        this.subList = subList;
    }

    @NonNull
    @Override
    public TeamSubRecyclerViewAdapter.SubVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_player, parent, false);
        return new SubVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamSubRecyclerViewAdapter.SubVH holder, int position) {

        SubPlayer sub = subList.get(position);

        holder.name.setText(sub.getName());

        int playerNum = 16 + position;
        holder.number.setText(playerNum);

    }

    @Override
    public int getItemCount() {
        return subList.size();
    }

    public class SubVH extends RecyclerView.ViewHolder {

        TextView name;
        TextView number;

        public SubVH(@NonNull final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.playerName);
            number = itemView.findViewById(R.id.playerNumber);

        }
    }
}
