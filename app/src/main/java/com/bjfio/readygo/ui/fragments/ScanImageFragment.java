package com.bjfio.readygo.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bjfio.readygo.R;
import com.bjfio.readygo.base.BaseFragment;
import com.bjfio.readygo.presenter.ScanImagePresenter;
import com.bjfio.readygo.presenter.contract.ScanImageContract;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/11/30.
 */
public class ScanImageFragment extends BaseFragment<ScanImagePresenter> implements ScanImageContract.View {

    public static final String ARGUMENT_URL = "URL";
    @BindView(R.id.photo_view)
    ImageView photoView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    String url;

    boolean isFinish = false;

    public static ScanImageFragment getInstance(String url) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_URL, url);
        ScanImageFragment fragment = new ScanImageFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_scan_item;
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        url = getArguments().getString(ARGUMENT_URL);
        mPresenter = new ScanImagePresenter(this, url);
        if (!isFinish)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void lazyFetchData() {
        mPresenter.getData();
    }

    @Override
    public void showContent(String response) {
        if (response != null) {
            Glide.with(this).load(response).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.SOURCE).
                    into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            progressBar.setVisibility(View.GONE);
                            photoView.setImageBitmap(resource);
                            isFinish = true;
                        }
                    });

        }
    }

    @Override
    public void refreshFaild(String msg) {
        showError(msg);
    }

}
