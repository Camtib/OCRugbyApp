package com.example.ocrugbyapp.results;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ocrugbyapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ResultsListAdapter extends RecyclerView.Adapter<ResultsListAdapter.ResultsVH> {

    private Context mContext;
    List<ResultsCard> resultsList;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    FirebaseUser user;
    String userID;

    public ResultsListAdapter(Context mContext, List<ResultsCard> resultsList) {
        this.mContext = mContext;
        this.resultsList = resultsList;
    }

    @NonNull
    @Override
    public ResultsListAdapter.ResultsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.results_row, parent, false);
        return new ResultsVH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ResultsListAdapter.ResultsVH holder, int position) {

        ResultsCard resultsCard = resultsList.get(position);

        holder.date.setText(resultsCard.getDate());
        holder.homeTeam.setText(resultsCard.getHomeTeam());
        holder.awayTeam.setText(resultsCard.getAwayTeam());
        holder.homeScore.setText(resultsCard.getHomeScore());
        holder.awayScore.setText(resultsCard.getAwayScore());

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public class ResultsVH extends RecyclerView.ViewHolder {

        TextView date, homeTeam, awayTeam, homeScore, awayScore;

        public ResultsVH(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.dateTV);
            homeTeam = itemView.findViewById(R.id.homeTeamTV);
            awayTeam = itemView.findViewById(R.id.awayTeamTV);
            homeScore = itemView.findViewById(R.id.homeScoreTV);
            awayScore = itemView.findViewById(R.id.awayScoreTV);
        }
    }
}
