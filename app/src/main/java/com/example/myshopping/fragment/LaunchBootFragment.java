package com.example.myshopping.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myshopping.LoginActivity;
import com.example.myshopping.R;

public class LaunchBootFragment extends Fragment {
    protected View mView;
    protected Context mContext;
    private int mPosition;
    private int mImageId;
    private int mCount = 4;

    //初始化对象，返回LaunchBootFragment
    public static LaunchBootFragment newInstance(int position, int image_id) {
        LaunchBootFragment fragment = new LaunchBootFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putInt("image_id", image_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position", 0);
            mImageId = getArguments().getInt("image_id", 0);
        }
        mView = inflater.inflate(R.layout.item_launch_boot, container, false);
        ImageView iv_launch = mView.findViewById(R.id.iv_launch);
        RadioGroup rg_indicate = mView.findViewById(R.id.rg_indicate);
        Button btn_start = mView.findViewById(R.id.btn_start);
        iv_launch.setImageResource(mImageId);
        for (int j = 0; j < mCount; j++) {
            RadioButton radio = new RadioButton(mContext);
            radio.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            radio.setButtonDrawable(R.drawable.launch_guide);
            radio.setPadding(10, 10, 10, 10);
            rg_indicate.addView(radio); // 把单选按钮添加到页面底部的单选组
        }
        //根据mPosition获得哪个单选按钮需要被选中
        ((RadioButton) rg_indicate.getChildAt(mPosition)).setChecked(true);
        //到最后一页，会有个跳转
        if (mPosition == mCount - 1) {
            //使按钮可见
            btn_start.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(btn_start, "translationX", 500f,0f);
            animator.setDuration(1000);
            animator.start();
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            });
        }
        return mView;
    }
}
