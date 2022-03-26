package com.example.myshopping.interceptor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.viewpager2.widget.ViewPager2;

public class MyLinearLayout extends LinearLayout {
    public static final String TAG = "MyLinearLayout";
    private int mLastX;
    private int mLastY;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        ViewPager2 pager2 = (ViewPager2) getChildAt(1);
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mLastX;
                float deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (pager2.getCurrentItem() == 0 && x - mLastX > 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(pager2.getCurrentItem() != pager2.getAdapter().getItemCount() - 1
                                || x - mLastX >= 0);
                    }
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;

        }
        mLastX = (int) x;
        mLastY = (int) y;
        return super.dispatchTouchEvent(ev);
    }
}
