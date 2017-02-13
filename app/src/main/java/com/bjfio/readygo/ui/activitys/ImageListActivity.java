package com.bjfio.readygo.ui.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bjfio.readygo.R;
import com.bjfio.readygo.base.BaseActivity;
import com.bjfio.readygo.ui.fragments.ImageListFragment;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/10.
 */
public class ImageListActivity extends BaseActivity {
    private static final String TAG = "ImageListActivity";

    int type = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        type = getIntent().getIntExtra(ImageListFragment.ARGUMENT_TYPE,-1);
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content, ImageListFragment.getInstance(type), TAG);
            ft.commit();
        }
    }

    @OnClick(R.id.back)
    public void goback() {
        finish();
    }

}
