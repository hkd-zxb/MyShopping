package com.example.myshopping.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myshopping.R;

public class LoginFragment extends Fragment {
    public static final String TAG="LoginFragment";
    protected View mView; // 声明一个视图对象
    protected Context mContext; // 声明一个上下文对象
    private RadioGroup rg_login;
    private RadioButton rb_cancel;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity(); // 获取活动页面的上下文
        Log.d(TAG, "onCreateView: ");
        // 根据布局文件fragment_static.xml生成视图对象
        mView = inflater.inflate(R.layout.fragment_login, container, false);
        rg_login = mView.findViewById(R.id.rg_login);
        rb_cancel = mView.findViewById(R.id.rb_cancel);
        rb_password = mView.findViewById(R.id.rb_password);
        rb_verifycode = mView.findViewById(R.id.rb_verifycode);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
//        ObjectAnimator.ofFloat(rb_cancel, "translationX", rb_cancel.getTranslationX(), -1000f, rb_cancel.getTranslationX()).setDuration(5000).start();
//        ObjectAnimator.ofFloat(rb_password, "translationX", rb_password.getTranslationX(), -1000f, rb_password.getTranslationX()).setDuration(3000).start();
//        ObjectAnimator.ofFloat(rb_verifycode, "translationX", rb_verifycode.getTranslationX(), -1000f, rb_verifycode.getTranslationX()).setDuration(4000).start();

    }
}
