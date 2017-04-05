package com.bjfio.readygo.ui.activitys;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bjfio.readygo.R;
import com.bjfio.readygo.base.BaseActivity;
import com.bjfio.readygo.model.net.MyException;
import com.bjfio.readygo.model.net.RetrofitHelper;
import com.bjfio.readygo.ui.adapter.BrowseImagePagerAdatper;
import com.bjfio.readygo.utils.EventUtil;
import com.bjfio.readygo.utils.FileUtil;
import com.bjfio.readygo.utils.RxUtil;
import com.bjfio.readygo.utils.ShareUtils;
import com.bjfio.readygo.utils.SystemUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;


public class BrowseImagesActivity extends BaseActivity {

    @BindView(R.id.vp)
    public ViewPager mViewPager;
    @BindView(R.id.index)
    public TextView txtIndex;
    public int mCurrentIndex = 1;
    @BindView(R.id.right_button)
    TextView shareBtn;
    @BindView(R.id.right_button1)
    TextView setWall;
    BrowseImagePagerAdatper mAdapter;
    List<String> imageUrls;
    String root;
    String shareImageUrl;
    String url;

    @OnClick(R.id.right_button)
    public void share() {
        if (imageUrls != null && !EventUtil.isFastDoubleClick())
            FileUtil.getPathByUrl(this, mAdapter.getItem(mCurrentIndex).getUrl())
                    .compose(RxUtil.<Uri>rxSchedulerHelper())
                    .subscribe(new Observer<Uri>() {
                        @Override
                        public void onCompleted() {
                            System.out.println("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("onError" + e.getMessage());
                            if (e instanceof MyException) {
                                EventUtil.showSnackbar(getRootView(BrowseImagesActivity.this), e.getMessage());
                            }
                        }

                        @Override
                        public void onNext(Uri uri) {
                            ShareUtils.shareImage(BrowseImagesActivity.this, uri, "分享福利...");
                        }
                    });
    }

    @OnClick(R.id.right_button1)
    public void setWall() {
        if (imageUrls != null && !EventUtil.isFastDoubleClick())
            FileUtil.getBitmapByUrl(this, mAdapter.getItem(mCurrentIndex).getUrl())
                    .compose(RxUtil.<Bitmap>rxSchedulerHelper())
                    .subscribe(new Observer<Bitmap>() {
                        @Override
                        public void onCompleted() {
                            System.out.println("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("onError" + e.getMessage());
                            if (e instanceof MyException) {
                                EventUtil.showSnackbar(getRootView(BrowseImagesActivity.this), e.getMessage());
                            }
                        }

                        @Override
                        public void onNext(Bitmap bitmap) {
                            WallpaperManager manager = WallpaperManager.getInstance(BrowseImagesActivity.this);
                            try {
                                manager.setBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            EventUtil.showSnackbar(getRootView(BrowseImagesActivity.this),"设置成功");
                        }
                    });
    }


    @OnClick(R.id.back)
    public void goback() {
        finish();
    }

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_rtys_detail);
        shareBtn.setVisibility(View.VISIBLE);
        shareBtn.setText("分享");
        setWall.setVisibility(View.VISIBLE);
        setWall.setText("设置为壁纸");

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        shareImageUrl = intent.getStringExtra("imageUrl");
        int index = url.lastIndexOf(".html");
        root = url.substring(0, index);
        loadTasks(url);
    }

    private void processData(List<String> list) {
        imageUrls = list;
        imageUrls.add(0, url);
        mCurrentIndex = 0;

        if (list != null && list.size() > 0) {
            txtIndex.setText(mCurrentIndex + 1 + "/" + list.size());
            mAdapter = new BrowseImagePagerAdatper(getSupportFragmentManager(), list);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(mCurrentIndex);
        }
    }

    public void loadTasks(final String url) {
        RetrofitHelper.getNewsApi().getDataFromUrl(url)
                .flatMap(new Func1<String, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(String s) {
                        Document response = Jsoup.parse(s);
                        if (response != null) {
                            return RxUtil.createData(parseToRtysDetail(response));
                        }
                        return Observable.error(new Exception());
                    }
                })
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> tasks) {
                        return Observable.from(tasks);
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return root + "_" + s + ".html";
                    }
                })
                .toList()
                .compose(RxUtil.<List<String>>rxSchedulerHelper())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        processData(strings);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("go");
                    }
                });
    }

    private List<String> parseToRtysDetail(Document document) {
        List<String> tasks = new ArrayList<>();
        Elements elements = document.select("div[class^=pages]");
        Elements pageElements = elements.get(0).select("li");
        if (!pageElements.isEmpty()) {
            String firstli = pageElements.get(0).text();
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(firstli);
            int total = Integer.parseInt(m.replaceAll("").trim());

            for (int i = 2; i < total; i++) {
                tasks.add(i + "");
            }
        }
        return tasks;
    }

    @OnPageChange(R.id.vp)
    void onPageSelected(int position) {
        mCurrentIndex = position;
        txtIndex.setText(String.valueOf(mCurrentIndex + 1 + "/" + imageUrls
                .size()));
    }

}
