package com.hu.stackcardview;

/**
 * Created by Cvmars on 2016/6/8.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;


/**
 * Created by Denny on 2015/8/1.
 */
public class DisallowParentTouchViewPager extends ViewPager {


    private ViewGroup parent;

    public DisallowParentTouchViewPager(Context context) {
        super(context);
    }

    public DisallowParentTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNestParent(ViewGroup parent) {
        this.parent = parent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean isFocused() {

        return true;
    }
}
