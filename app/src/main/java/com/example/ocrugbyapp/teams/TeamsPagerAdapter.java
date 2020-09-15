package com.example.ocrugbyapp.teams;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class TeamsPagerAdapter extends FragmentStateAdapter {


    public TeamsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new FirstTeam();
            }
            case 1: {

                return new SecondTeam();
            }
            case 2: {
                return new BTeam();
            }
            case 3: {
                return new WomensTeam();
            }
            default:
                return new FirstTeam();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
