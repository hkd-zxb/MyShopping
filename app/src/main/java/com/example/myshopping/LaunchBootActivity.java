package com.example.myshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.myshopping.adapter.LaunchBootAdapter;

public class LaunchBootActivity extends AppCompatActivity {
    private int[] lanuchImageArray = {R.drawable.guide_bg1,
            R.drawable.guide_bg2, R.drawable.guide_bg3, R.drawable.guide_bg4};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_boot);
        ViewPager2 vp_launch=findViewById(R.id.vp_launch);
        LaunchBootAdapter adapter=new LaunchBootAdapter(this,lanuchImageArray);
        vp_launch.setAdapter(adapter);
        vp_launch.setCurrentItem(0);
    }
}