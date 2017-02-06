package com.bjfio.readygo.ui.activitys;

import android.content.Intent;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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
    BrowseImagePagerAdatper mAdapter;
    List<String> imageUrls;
    String root;
    String shareImageUrl;

    @OnClick(R.id.right_button)
    public void share() {
        if (imageUrls != null && !EventUtil.isFastDoubleClick())
            FileUtil.getPathByUrl(this, shareImageUrl)
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

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        shareImageUrl = intent.getStringExtra("imageUrl");
        int index = url.lastIndexOf("/");
        root = url.substring(0, index + 1);
        loadTasks(url);
    }

    private void processData(List<String> list) {
        imageUrls = list;
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
                        return root + s;
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

                    }
                });
    }

    private List<String> parseToRtysDetail(Document document) {
        Elements elements = document.select("li[class^=pageAll]");
        List<String> tasks = new ArrayList<>();
        for (Element element : elements) {
            Elements hrefElement = element.select("a");
            String href = hrefElement.attr("href");
            System.out.println("href ------->" + href);
            tasks.add(href);
        }

        if (tasks != null) {
            tasks.remove(0);
        }
        return tasks;
    }

    @OnPageChange(R.id.vp)
    void onPageSelected(int position) {
        txtIndex.setText(String.valueOf(position + 1 + "/" + imageUrls
                .size()));
    }

}
