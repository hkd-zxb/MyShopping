package com.example.myshopping.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myshopping.fragment.DepartmentCartFragment;
import com.example.myshopping.fragment.DepartmentClassFragment;
import com.example.myshopping.fragment.DepartmentHomeFragment;
import com.example.myshopping.fragment.MessageFragment;
import com.example.myshopping.fragment.MineFragment;


public class DepartmentPagerAdapter extends FragmentStateAdapter {
    public DepartmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DepartmentHomeFragment();
            case 1:
                return new DepartmentClassFragment();
            case 2:
                return new MessageFragment();
            case 3:
                return new DepartmentCartFragment();
            default:
                return new MineFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
