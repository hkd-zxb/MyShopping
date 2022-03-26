package com.example.myshopping.interceptor;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import androidx.viewpager2.widget.ViewPager2;

import com.example.myshopping.R;

public class MyLinearLayoutHome extends ScrollView {
    public static final String TAG = "MyLinearLayout";
    private int mLastX;
    private int mLastY;

    public MyLinearLayoutHome(Context context) {
        super(context);
    }

    public MyLinearLayoutHome(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayoutHome(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
//        ViewPager2 viewPager2 = (ViewPager2) getChildAt(1);
//        Log.d(TAG, "onInterceptTouchEvent: 0"+getChildAt(0)+"1"+getChildAt(1)+"2"+getChildAt(2));
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mLastX;
                float deltaY = y - mLastY;
                if (Math.abs(deltaX) < Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;

        }
        mLastX = (int) x;
        mLastY = (int) y;
        return super.dispatchTouchEvent(ev);
    }



}
