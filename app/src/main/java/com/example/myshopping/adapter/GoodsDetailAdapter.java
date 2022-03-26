package com.example.myshopping.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myshopping.fragment.GoodsDetailFragment;

import java.util.List;

public class GoodsDetailAdapter extends FragmentStateAdapter {
    private List<String> mTitleList;

    public GoodsDetailAdapter(@NonNull FragmentActivity fragmentActivity, List<String> mTitleList) {
        super(fragmentActivity);
        this.mTitleList = mTitleList;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new GoodsDetailFragment();
    }


    @Override
    public int getItemCount() {
        return 1;
    }
}
