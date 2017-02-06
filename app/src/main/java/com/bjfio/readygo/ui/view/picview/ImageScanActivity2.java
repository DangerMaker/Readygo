package com.bjfio.readygo.ui.view.picview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjfio.readygo.R;
import com.bjfio.readygo.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ImageScanActivity2 extends BaseActivity{

    @BindView(R.id.vp)
    public ViewPager mViewPager;
    @BindView(R.id.index)
    public TextView txtIndex;

    public ImageScanAdapter1 mAdapter;
    public LayoutInflater mInflater;

    public int mCurrentIndex = 1;
    public List<String> urls;
    String root;

    @OnClick(R.id.back)
    public void goback(){
        finish();
    }

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_rtys_detail);
        unbinder = ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        int index = url.lastIndexOf("/");
        root = url.substring(0, index + 1);
        loadTasks(url);
    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, ImageScanActivity2.class);
        intent.putExtra("url", url);
        return intent;
    }


    private void processData() {

        mCurrentIndex = 0;
        if (urls != null && mCurrentIndex >= urls.size()) {
            mCurrentIndex = urls.size() - 1;
        }
        if (mCurrentIndex < 0) {
            mCurrentIndex = 0;
        }
        if (urls != null && urls.size() > 0) {
            txtIndex.setText(mCurrentIndex + 1 + "/" + urls.size());
            mAdapter = new ImageScanAdapter1();
            mAdapter.setList(urls);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(mCurrentIndex);

        }
    }

    public void loadTasks(final String url) {
//        RytsManager.getInstance()
//                .getRtysDetail(url)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<String>>() {
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("onError");
//                    }
//
//                    @Override
//                    public void onNext(List<String> tasks) {
//                        System.out.println("onNext");
//                        urls = tasks;
//                        processData();
//                    }
//                });
    }

    @OnPageChange(R.id.vp)
    void onPageSelected(int position){
        txtIndex.setText(String.valueOf(position + 1 + "/" + mAdapter.getCount()));
    }

    public class ImageScanAdapter1 extends SimplePagerAdapter<String> {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public View getItemView(ViewGroup container, int position) {

            View view = LayoutInflater.from(ImageScanActivity2.this).inflate(R.layout.image_scan_item, container, false);
            final PhotoView image = (PhotoView) view
                    .findViewById(R.id.photo_view);
//            final ProgressBar pb = (ProgressBar) view
//                    .findViewById(R.id.progress);
//            pb.setVisibility(View.VISIBLE);

//            RytsManager.getInstance().getDetailImageUrl(root + list.get(position))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<String>() {
//                        @Override
//                        public void onCompleted() {
//                            System.out.println("onCompleted");
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            System.out.println("onError");
//                        }
//
//                        @Override
//                        public void onNext(String tasks) {
//                            System.out.println("onNext");
//                            Glide.with(ImageScanActivity2.this).load(tasks).asBitmap().
//                                    placeholder(getResources().getDrawable(R.drawable.pc_default_error_image)).
//                                    diskCacheStrategy(DiskCacheStrategy.SOURCE).
//                                    into(new SimpleTarget<Bitmap>() {
//                                        @Override
//                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                            image.setImageBitmap(resource);
//                                        }
//                                    });
//                        }
//                    });

            return view;
        }

    }

    public class SimplePagerAdapter<T> extends PagerAdapter {

        public List<T> list = new ArrayList<T>();

        public SimplePagerAdapter() {
        }

        public SimplePagerAdapter(List<T> list) {
            setList(list);
        }

        public List<T> getList() {
            return list;
        }

        public void setList(List<T> list) {
            if (list == null) return;
            this.list = list;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            if (list != null && list.size() > 0) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getItemView(container, position);
            container.addView(view);
            return view;
        }

        public View getItemView(ViewGroup container, int position) {

            return new View(container.getContext());
        }

        public T getItem(int position) {
            return list.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }


}
