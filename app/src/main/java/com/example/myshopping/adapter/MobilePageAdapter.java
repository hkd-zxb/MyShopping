package com.example.myshopping.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myshopping.bean.GoodsInfo;
import com.example.myshopping.fragment.MobileFragment;

import java.util.ArrayList;
import java.util.List;

public class MobilePageAdapter extends FragmentStateAdapter {
    private List<GoodsInfo> mGoodsInfos;

    public MobilePageAdapter(@NonNull FragmentActivity fragmentActivity, List<GoodsInfo> mGoodsInfos) {
        super(fragmentActivity);
        this.mGoodsInfos = mGoodsInfos;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return MobileFragment.newInstance(position, mGoodsInfos.get(position).pic, mGoodsInfos.get(position).desc);
    }

    @Override
    public int getItemCount() {
        return mGoodsInfos.size();
    }
}
