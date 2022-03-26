package com.example.myshopping.interceptor;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.material.tabs.TabLayout;

public class MyTabLayOut extends TabLayout {
    public static final String TAG = "MyTabLayOut";
    private int mLastX;
    private int mLastY;

    public MyTabLayOut(Context context) {
        super(context);
    }

    public MyTabLayOut(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTabLayOut(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        Log.d(TAG, "dispatchTouchEvent: " + this.getSelectedTabPosition());
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.getSelectedTabPosition() == 1 && x - mLastX > 0 || this.getSelectedTabPosition() == 0 && x - mLastX < 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    Log.d(TAG, "dispatchTouchEvent: "+(x-mLastX));
                }


                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = (int) x;
        mLastY = (int) y;
        return super.dispatchTouchEvent(ev);
    }
}
