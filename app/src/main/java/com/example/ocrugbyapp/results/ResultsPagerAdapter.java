package com.example.ocrugbyapp.results;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ResultsPagerAdapter extends FragmentStateAdapter {

    public ResultsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0: {
                return new FirstsResults();
            }
            case 1: {

                return new SecondsResults();
            }
            case 2: {
                return new BsResults();
            }
            default:
                return new FirstsResults();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
