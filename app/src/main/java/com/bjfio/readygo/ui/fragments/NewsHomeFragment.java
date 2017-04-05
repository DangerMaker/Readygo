package com.bjfio.readygo.ui.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.bjfio.readygo.R;
import com.bjfio.readygo.base.BaseFragment;
import com.bjfio.readygo.event.HomeTabEvent;
import com.bjfio.readygo.ui.adapter.EasyPagerAdatper;
import com.bjfio.readygo.ui.view.AmazingToolbar;


import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Observer;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/11/17.
 */
public class NewsHomeFragment extends BaseFragment{
    protected final String TAG = this.getClass().getSimpleName();

    EasyPagerAdatper mContainerAdapter;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.choices_toolbar)
    AmazingToolbar toolbar;
    ArrayList<EasyFragment> vestData;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news_home;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        vestData = new ArrayList<>();
        EasyFragment fragment1 = new EasyFragment(ImageListFragment.getInstance(ImageListFragment.TYPE_SEXY), "高清套图");
        EasyFragment fragment2 = new EasyFragment(new ChoiceFragment(), "精选");
//        EasyFragment fragment3 = new EasyFragment(EmptyFragment.getInstance("child","2"), "福利");

        vestData.add(fragment1);
        vestData.add(fragment2);
//        vestData.add(fragment3);

        mContainerAdapter = new EasyPagerAdatper(getChildFragmentManager(),vestData);
        mViewPager.setOffscreenPageLimit(mContainerAdapter.getCount());
        mViewPager.setAdapter(mContainerAdapter);
        toolbar.setViewPager(mViewPager, mContainerAdapter);

        if (1 == mViewPager.getCurrentItem())
            return;

        mViewPager.setCurrentItem(1, false);
        Log.e(TAG, "initView: " + mViewPager.getCurrentItem() );
        toolbar.notifyDataSetChanged();
    }

//    @Subscriber(tag = "tab", mode = ThreadMode.MAIN)
//    public void updateTab(HomeTabEvent event){
//        toolbar.notifyDataSetChanged("美女图片");
//        mViewPager.setCurrentItem(0);
//        RtysFragment fragment = (RtysFragment) mContainerAdapter.getItem(0);
//
//    }

}
