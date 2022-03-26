package com.example.myshopping.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myshopping.fragment.LaunchBootFragment;

public class LaunchBootAdapter extends FragmentStateAdapter {
    private int[] mImageArray;

    public LaunchBootAdapter(@NonNull FragmentActivity fragmentActivity, int[] mImageArray) {
        super(fragmentActivity);
        this.mImageArray = mImageArray;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return LaunchBootFragment.newInstance(position,mImageArray[position]);
    }

    @Override
    public int getItemCount() {
        return mImageArray.length;
    }
}
