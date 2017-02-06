package com.bjfio.readygo.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import butterknife.Unbinder;

/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public abstract class RootView extends LinearLayout {
    /**
     * 是否被销毁
     */
    protected boolean mActive;
    protected Context mContext;
//    protected Unbinder unbinder;
    protected boolean isInit = false;

    public RootView(Context context) {
        super(context);
    }

    public RootView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mActive = true;
//        if (!isInit)
            init();
    }

    protected abstract void init();

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mActive = false;
//        if (unbinder != null)
//            unbinder.unbind();
        mContext = null;
    }
}
