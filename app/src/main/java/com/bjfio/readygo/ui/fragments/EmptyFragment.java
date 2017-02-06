package com.bjfio.readygo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.bjfio.readygo.R;
import com.bjfio.readygo.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/1.
 */
public class EmptyFragment extends BaseFragment {

    public static final String ARGUMENT_LEVEL = "LEVEL";
    public static final String ARGUMENT_POS = "POS";


    @BindView(R.id.textView)
    TextView textView;

    String level;
    String position;

    public static EmptyFragment getInstance(String level,String position){
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_LEVEL, level);
        arguments.putString(ARGUMENT_POS, position);
        EmptyFragment fragment = new EmptyFragment();
        fragment.setArguments(arguments);
        return  fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        level = getArguments().getString(ARGUMENT_LEVEL);
        position = getArguments().getString(ARGUMENT_POS);
        textView.setText("LEVEL: " + level + " POS:" + position);
    }
}
