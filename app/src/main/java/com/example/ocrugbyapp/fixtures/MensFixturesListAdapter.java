package com.example.ocrugbyapp.fixtures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ocrugbyapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;


public class MensFixturesListAdapter extends RecyclerView.Adapter<MensFixturesListAdapter.FixturesVH> {

    private Context mContext;
    List<MensFixtureCard> fixturesList;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    FirebaseUser user;
    String userID;

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

        // no fixtures
        if (holder.firstsOpposition.getText().toString().equals("No Fixture") &
                holder.secondsOpposition.getText().toString().equals("No Fixture") &
                holder.bsOpposition.getText().toString().equals("No Fixture")) {

            holder.fixtureNumber.setText("");

            // only 1st XV fixture
        } else if (!holder.firstsOpposition.getText().toString().equals("No Fixture") &
                holder.secondsOpposition.getText().toString().equals("No Fixture") &
                holder.bsOpposition.getText().toString().equals("No Fixture")) {

            holder.fixtureNumber.setText(mContext.getResources().getString(R.string.first));

            // only 2nd XV fixture
        } else if (holder.firstsOpposition.getText().toString().equals("No Fixture") &
                !holder.secondsOpposition.getText().toString().equals("No Fixture") &
                holder.bsOpposition.getText().toString().equals("No Fixture")) {

            holder.fixtureNumber.setText(mContext.getResources().getString(R.string.second));

            // only B XV fixture
        } else if (holder.firstsOpposition.getText().toString().equals("No Fixture") &
                holder.secondsOpposition.getText().toString().equals("No Fixture") &
                !holder.bsOpposition.getText().toString().equals("No Fixture")) {

            holder.fixtureNumber.setText(mContext.getResources().getString(R.string.b));

            // 1st XV and 2nd XV fixtures
        } else if (!holder.firstsOpposition.getText().toString().equals("No Fixture") &
                !holder.secondsOpposition.getText().toString().equals("No Fixture") &
                holder.bsOpposition.getText().toString().equals("No Fixture")) {

            String haveFixture = mContext.getResources().getString(R.string.first) + "/" + mContext.getResources().getString(R.string.second);
            holder.fixtureNumber.setText(haveFixture);

            // 1st XV and B XV fixtures
        } else if (!holder.firstsOpposition.getText().toString().equals("No Fixture") &
                holder.secondsOpposition.getText().toString().equals("No Fixture") &
                !holder.bsOpposition.getText().toString().equals("No Fixture")) {

            String haveFixture = mContext.getResources().getString(R.string.first) + "/" + mContext.getResources().getString(R.string.b);
            holder.fixtureNumber.setText(haveFixture);

            // 2nd XV and B XV fixtures
        } else if (holder.firstsOpposition.getText().toString().equals("No Fixture") &
                !holder.secondsOpposition.getText().toString().equals("No Fixture") &
                !holder.bsOpposition.getText().toString().equals("No Fixture")) {

            String haveFixture = mContext.getResources().getString(R.string.second) + "/" + mContext.getResources().getString(R.string.b);
            holder.fixtureNumber.setText(haveFixture);

            // all 3 teams have fixtures
        } else if (!holder.firstsOpposition.getText().toString().equals("No Fixture") &
                !holder.secondsOpposition.getText().toString().equals("No Fixture") &
                !holder.bsOpposition.getText().toString().equals("No Fixture")) {

            String haveFixture = mContext.getResources().getString(R.string.first) + "/" + mContext.getResources().getString(R.string.second) + "/" + mContext.getResources().getString(R.string.b);
            holder.fixtureNumber.setText(haveFixture);

        }

        if (position == 0) {
            holder.expandableInfo.setVisibility(View.VISIBLE);
            holder.itemView.findViewById(R.id.toolBar).setVisibility(View.INVISIBLE);
            holder.itemView.findViewById(R.id.toolBar2).setVisibility(View.INVISIBLE);
            holder.itemView.findViewById(R.id.infoTV).setVisibility(View.INVISIBLE);
            holder.itemView.setClickable(false);
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.boarder));

            holder.firstsOpposition.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.firstsHA.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.firstsLC.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.firstsKO.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.firstsMeet.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.firstsAddress.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.firstsPostcode.setTextColor(mContext.getResources().getColor(R.color.black));

            holder.secondsOpposition.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.secondsHA.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.secondsLC.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.secondsKO.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.secondsMeet.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.secondsAddress.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.secondsPostcode.setTextColor(mContext.getResources().getColor(R.color.black));

            holder.bsOpposition.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.bsHA.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.bsLC.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.bsKO.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.bsMeet.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.bsAddress.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.bsPostcode.setTextColor(mContext.getResources().getColor(R.color.black));

        } else {
            holder.itemView.findViewById(R.id.availabilitySwitch).setVisibility(View.GONE);
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
        CheckBox checkBox;

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

            checkBox = itemView.findViewById(R.id.availabilitySwitch);

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

            mAuth = FirebaseAuth.getInstance();
            mStore = FirebaseFirestore.getInstance();
            if (mAuth.getCurrentUser() != null) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                userID = user.getUid();

                DocumentReference documentReference = mStore.collection("users").document(userID);

                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.get("Available").equals(true)) {
                            checkBox.setChecked(true);
                        }else {
                            checkBox.setChecked(false);
                        }
                    }
                });

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            documentReference.update("Available", true);
                        } else {
                            documentReference.update("Available", false);
                        }
                    }
                });
            }
        }
    }
}
