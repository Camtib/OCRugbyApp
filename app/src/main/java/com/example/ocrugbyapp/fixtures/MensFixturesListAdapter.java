package com.example.ocrugbyapp.fixtures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ocrugbyapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


public class MensFixturesListAdapter extends RecyclerView.Adapter<MensFixturesListAdapter.FixturesVH> {

    private Context mContext;
    List<MensFixtureCard> fixturesList;

    public MensFixturesListAdapter(Context mContext, List<MensFixtureCard> fixturesList) {
        this.mContext = mContext;
        this.fixturesList = fixturesList;

    }


    @NonNull
    @Override
    public FixturesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mens_fixtures, parent, false);
        return new FixturesVH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FixturesVH holder, int position) {

        MensFixtureCard fixtures = fixturesList.get(position);
        holder.fixtureNumber.setText(fixtures.getFixtureNum());
        holder.date.setText(fixtures.getDate());

        holder.firstsOpposition.setText(fixtures.getFirstsFixture());
        holder.firstsHA.setText(fixtures.getFirstsHA());
        holder.firstsLC.setText(fixtures.getFirstsLC());
        holder.firstsKO.setText(fixtures.getFirstsKO());
        holder.firstsMeet.setText(fixtures.getFirstsMeet());
        holder.firstsAddress.setText(fixtures.getFirstsAddress());
        holder.firstsPostcode.setText(fixtures.getFirstsPostcode());

        holder.secondsOpposition.setText(fixtures.getSecondsFixture());
        holder.secondsHA.setText(fixtures.getSecondsHA());
        holder.secondsLC.setText(fixtures.getSecondsLC());
        holder.secondsKO.setText(fixtures.getSecondsKO());
        holder.secondsMeet.setText(fixtures.getSecondsMeet());
        holder.secondsAddress.setText(fixtures.getSecondsAddress());
        holder.secondsPostcode.setText(fixtures.getSecondsPostcode());

        holder.bsOpposition.setText(fixtures.getBsFixture());
        holder.bsHA.setText(fixtures.getBsHA());
        holder.bsLC.setText(fixtures.getBsLC());
        holder.bsKO.setText(fixtures.getBsKO());
        holder.bsMeet.setText(fixtures.getBsMeet());
        holder.bsAddress.setText(fixtures.getBsAddress());
        holder.bsPostcode.setText(fixtures.getBsPostcode());

        if (position == 0) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.lighterBlueForFixtureList));
            holder.expandableInfo.setVisibility(View.VISIBLE);
            holder.itemView.findViewById(R.id.toolBar).setVisibility(View.INVISIBLE);
            holder.itemView.findViewById(R.id.infoTV).setVisibility(View.GONE);


        } else {
            boolean isExpandable = fixturesList.get(position).isExpandable();
            holder.expandableInfo.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return fixturesList.size();
    }

    public class FixturesVH extends RecyclerView.ViewHolder {

        TextView date, fixtureNumber;
        TextView firstsOpposition, firstsLC, firstsHA, firstsKO, firstsMeet, firstsAddress, firstsPostcode;
        TextView secondsOpposition, secondsLC, secondsHA, secondsKO, secondsMeet, secondsAddress, secondsPostcode;
        TextView bsOpposition, bsLC, bsHA, bsKO, bsMeet, bsAddress, bsPostcode;
        ConstraintLayout parent;
        ConstraintLayout expandableInfo, firstsInfoList, secondsInfoList, bsInfoList;
        TabLayout tabLayout;

        public FixturesVH(@NonNull final View itemView) {
            super(itemView);

            fixtureNumber = itemView.findViewById(R.id.fixtureNumber);
            date = itemView.findViewById(R.id.dateTV);

            firstsOpposition = itemView.findViewById(R.id.firstsOpposition);
            firstsLC = itemView.findViewById(R.id.firstsLC);
            firstsHA = itemView.findViewById(R.id.firstsHA);
            firstsKO = itemView.findViewById(R.id.firstsKO);
            firstsMeet = itemView.findViewById(R.id.firstsMeet);
            firstsAddress = itemView.findViewById(R.id.firstsAddress);
            firstsPostcode = itemView.findViewById(R.id.firstsPostcode);

            secondsOpposition = itemView.findViewById(R.id.secondsOpposition);
            secondsLC = itemView.findViewById(R.id.secondsLC);
            secondsHA = itemView.findViewById(R.id.secondsHA);
            secondsKO = itemView.findViewById(R.id.secondsKO);
            secondsMeet = itemView.findViewById(R.id.secondsMeet);
            secondsAddress = itemView.findViewById(R.id.secondsAddress);
            secondsPostcode = itemView.findViewById(R.id.secondsPostcode);

            bsOpposition = itemView.findViewById(R.id.bsOpposition);
            bsLC = itemView.findViewById(R.id.bsLC);
            bsHA = itemView.findViewById(R.id.bsHA);
            bsKO = itemView.findViewById(R.id.bsKO);
            bsMeet = itemView.findViewById(R.id.bsMeet);
            bsAddress = itemView.findViewById(R.id.bsAddress);
            bsPostcode = itemView.findViewById(R.id.bsPostcode);

            firstsInfoList = itemView.findViewById(R.id.firstsInfoConstraintLayout);
            secondsInfoList = itemView.findViewById(R.id.secondsInfoConstraintLayout);
            bsInfoList = itemView.findViewById(R.id.bsInfoConstraintLayout);

            tabLayout = itemView.findViewById(R.id.tabLayout);

            tabLayout.getTabAt(0).setText("1st XV");
            tabLayout.getTabAt(1).setText("2nd XV");
            tabLayout.getTabAt(2).setText("B XV");

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tabLayout.getSelectedTabPosition() == 0) {
                        firstsInfoList.setVisibility(View.VISIBLE);
                        secondsInfoList.setVisibility(View.GONE);
                        bsInfoList.setVisibility(View.GONE);

                    } else if (tabLayout.getSelectedTabPosition() == 1) {
                        firstsInfoList.setVisibility(View.GONE);
                        secondsInfoList.setVisibility(View.VISIBLE);
                        bsInfoList.setVisibility(View.GONE);


                    } else if (tabLayout.getSelectedTabPosition() == 2) {
                        firstsInfoList.setVisibility(View.GONE);
                        secondsInfoList.setVisibility(View.GONE);
                        bsInfoList.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            parent = itemView.findViewById(R.id.parent);
            expandableInfo = itemView.findViewById(R.id.expandedInfoLayout);

            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MensFixtureCard fixtures = fixturesList.get(getAdapterPosition());
                    fixtures.setExpandable(!fixtures.isExpandable());
                    notifyItemChanged(getAdapterPosition());

                }
            });
        }
    }
}
