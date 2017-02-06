package com.bjfio.readygo.ui.activitys;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;

import com.bjfio.readygo.R;
import com.bjfio.readygo.app.App;
import com.bjfio.readygo.base.BaseActivity;
import com.bjfio.readygo.ui.adapter.HomePageAdapter;
import com.bjfio.readygo.ui.fragments.EmptyFragment;
import com.bjfio.readygo.ui.fragments.NewsHomeFragment;
import com.bjfio.readygo.ui.service.TestService;
import com.bjfio.readygo.utils.EventUtil;
import com.bjfio.readygo.widget.UnScrollViewPager;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.layout_home)
    View layout_YoukuHome;
    @BindView(R.id.layout_channel)
    View layout_YoukuChannel;
    @BindView(R.id.layout_subscribe)
    View layout_YouKuSubscribe;
    @BindView(R.id.layout_vip)
    View layout_YoukuVip;
    @BindView(R.id.layout_user)
    View layout_YoukuUser;
    @BindView(R.id.home_pager)
    UnScrollViewPager viewpager;

    HomePageAdapter homeAdapter;
    private int initPos = 0;
    private Long firstTime = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            initPos = savedInstanceState.getInt("pos");
        }

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        initView();
    }


    @Override
    protected void initView() {
        viewpager.setScrollable(false);
        homeAdapter = new HomePageAdapter(getSupportFragmentManager());
        viewpager.setAdapter(homeAdapter);
        viewpager.setOffscreenPageLimit(homeAdapter.getCount());
        switchTab(initPos);
    }

    public void switchTab(int paramInt) {
        this.layout_YoukuChannel.setSelected(false);
        this.layout_YoukuHome.setSelected(false);
        this.layout_YouKuSubscribe.setSelected(false);
        this.layout_YoukuUser.setSelected(false);
        this.layout_YoukuVip.setSelected(false);
        this.layout_YoukuChannel.setEnabled(true);
        this.layout_YoukuHome.setEnabled(true);
        this.layout_YouKuSubscribe.setEnabled(true);
        this.layout_YoukuUser.setEnabled(true);
        this.layout_YoukuVip.setEnabled(true);
        View localView = null;
        switch (paramInt) {
            default:
                return;
            case 0:
                localView = this.layout_YoukuHome;
                break;
            case 1:
                localView = this.layout_YoukuChannel;
                break;
            case 2:
                localView = this.layout_YouKuSubscribe;
                break;
            case 3:
                localView = this.layout_YoukuVip;
                break;
            case 4:
                localView = this.layout_YoukuUser;
                break;
        }

        if (localView != null) {
            localView.setSelected(true);
            localView.setEnabled(true);
        }
        if (viewpager.getCurrentItem() != paramInt) {
            viewpager.setCurrentItem(paramInt);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("pos", viewpager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }


    @OnClick({R.id.layout_home, R.id.layout_channel, R.id.layout_subscribe, R.id.layout_vip, R.id.layout_user})
    public void switchtab(View view) {
        switch (view.getId()) {
            case R.id.layout_home:
                switchTab(0);
                break;
            case R.id.layout_channel:
                switchTab(1);
                break;
            case R.id.layout_subscribe:
                switchTab(2);
                break;
            case R.id.layout_vip:
                switchTab(3);
                break;
            case R.id.layout_user:
                switchTab(4);
                break;
        }
    }

    @Override
    public void onBackPressedSupport() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 1500) {
            EventUtil.showToast(this, "再按一次退出");
            firstTime = secondTime;
        } else {
            App.getInstance().exitApp();
        }
    }
}
