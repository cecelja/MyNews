package com.example.mynews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager mf){
        super(mf);
    }

    //This method enables us to control on which position which fragment should be initialized
    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new TechFragment();
        } else if (position == 1){
            return new PoliticsFragment();
        } else {
            return new EconomicsFragment();
        }
    }
    //In order four the fragment adapter to know how many positions are there
    //We need to implement this method
    @Override
    public int getCount() {
        return 3;
    }

    //Set the titles of the fragments on the viewpager
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "Tech";
        } else if (position == 1){
            return "Politics";
        } else return "Economics";
    }
}
