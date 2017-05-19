package com.bjfio.readygo.ui.fragments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bjfio.readygo.R;
import com.bjfio.readygo.base.BaseFragment;
import com.bjfio.readygo.ui.activitys.LoginActivity;
import com.bjfio.readygo.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuxiaoyu on 2017/5/13.
 */

public class CenterFragment extends BaseFragment {
    @BindView(R.id.bg)
    RelativeLayout bg;

    @Override
    protected int getLayoutResource() {
        return R.layout.framgment_center;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) bg.getLayoutParams();
        ll.width = ScreenUtil.getScreenWidth(_mActivity);
        ll.height = ll.width * 2/3;
    }

    @OnClick(R.id.user)
    public void login(){
        startActivity(new Intent(_mActivity, LoginActivity.class));
    }
}
