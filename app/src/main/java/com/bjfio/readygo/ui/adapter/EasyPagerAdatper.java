package com.bjfio.readygo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.bjfio.readygo.ui.fragments.EasyFragment;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/11/27.
 */
public class EasyPagerAdatper extends FragmentPagerAdapter {

    private List<EasyFragment> fragments;

    public EasyPagerAdatper(FragmentManager fm, List<EasyFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getName();
    }
}
