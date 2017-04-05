package com.bjfio.readygo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.bjfio.readygo.ui.fragments.EasyFragment;
import com.bjfio.readygo.ui.fragments.EmptyFragment;
import com.bjfio.readygo.ui.fragments.NewsHomeFragment;
import com.bjfio.readygo.ui.fragments.ScanImageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/27.
 */
public class BrowseImagePagerAdatper extends FragmentStatePagerAdapter {

    private final String TAG = this.getClass().getSimpleName();
    private SparseArray<ScanImageFragment> fragments;
    List<String> list;

    public BrowseImagePagerAdatper(FragmentManager fm,List<String> list) {
        super(fm);
        this.list = list;
        fragments = new SparseArray<>();
    }

    @Override
    public ScanImageFragment getItem(int position) {
        ScanImageFragment fragment = fragments.get(position);
        if (fragment == null) {
            fragment = CreateFragment(position);
            fragments.put(position, fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e(TAG, "instantiateItem: " + position );
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        Log.e(TAG, "destroyItem: " + position );

    }

    private ScanImageFragment CreateFragment(int arg) {
        return ScanImageFragment.getInstance(list.get(arg));
    }
}
