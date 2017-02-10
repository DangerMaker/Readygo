package com.bjfio.readygo.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bjfio.readygo.R;
import com.bjfio.readygo.base.BaseFragment;
import com.bjfio.readygo.model.bean.BannerInfo;
import com.bjfio.readygo.model.bean.BeautyInfo;
import com.bjfio.readygo.model.bean.HairInfo;
import com.bjfio.readygo.model.bean.RecentInfo;
import com.bjfio.readygo.model.bean.SexyInfo;
import com.bjfio.readygo.model.bean.Task;
import com.bjfio.readygo.model.bean.WeiMeiInfo;
import com.bjfio.readygo.model.net.RetrofitHelper;
import com.bjfio.readygo.ui.adapter.ChoiceAdapter;
import com.bjfio.readygo.utils.RxUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/2/8.
 */
public class ChoiceFragment extends BaseFragment {

    @BindView(R.id.recycler)
    EasyRecyclerView mRecyclerView;

    ChoiceAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_choice;
    }

    @Override
    protected void initView(LayoutInflater inflater) {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        FrameLayout frameLayout = new FrameLayout(mContext);
        ProgressBar progressBar = new ProgressBar(mContext);
        FrameLayout.LayoutParams vlp = new FrameLayout.LayoutParams(200,200);
        vlp.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(vlp);
        frameLayout.addView(progressBar);

        mRecyclerView.setProgressView(frameLayout);
        adapter = new ChoiceAdapter(mContext);
        mRecyclerView.setAdapterWithProgress(adapter);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        RetrofitHelper.getImageApi().getMainUrl().
                flatMap(new Func1<String, Observable<List<Object>>>() {
                    @Override
                    public Observable<List<Object>> call(String s) {
                        Document response = Jsoup.parse(s);
                        if (response != null) {
                            return RxUtil.createData(parse(response));
                        }
                        return Observable.error(new Exception());
                    }
                })
                .compose(RxUtil.<List<Object>>rxSchedulerHelper())
                .subscribe(new Action1<List<Object>>() {
                    @Override
                    public void call(final List<Object> res) {
                        if (res != null) {
                            if (mActive) {
                                adapter.addAll(res);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    private List<Object> parse(Document document) {
        List<Object> list = new ArrayList<>();
        Elements elements = document.select("div[class^=w960 r]");
        Element elementMain = elements.get(0);

        Elements elementBanner = elementMain.select("div[class^=hd l re]");
        Elements bannerinfos = elementBanner.select("a");
        BannerInfo bannerinfo = new BannerInfo();
        for (Element info : bannerinfos){
            Elements a = info.select("a");
            String title = a.attr("imgsm");
            String href = a.attr("href");
            String img = a.select("img").attr("src");
            System.out.println(title);
            System.out.println(href);
            System.out.println(img);
            Task task = new Task(title,img,href,"");
            bannerinfo.getList().add(task);
        }
        list.add(bannerinfo);

        Elements elementRecent = elementMain.select("div[class^=w240 r BGfff]");
        Elements recentinfos = elementRecent.select("li");
        RecentInfo recentinfo = new RecentInfo();
        for (Element info : recentinfos){
            Elements a = info.select("a");
            String title = a.attr("title");
            String href = a.attr("href");
            String img = a.select("img").attr("src");
            System.out.println(title);
            System.out.println(href);
            System.out.println(img);
            Task task = new Task(title,img,href,"");
            recentinfo.getList().add(task);
        }
        list.add(recentinfo);

        Elements elementWeiMei = elementMain.select("div[class^=Cstyle1]");
        Elements weimeiinfos = elementWeiMei.select("li");
        WeiMeiInfo weimeiinfo = new WeiMeiInfo();
        for (Element info : weimeiinfos){
            Elements a = info.select("a");
            String title = a.select("img").attr("alt");
            String href = a.attr("href");
            String img = a.select("img").attr("src");
            System.out.println(title);
            System.out.println(href);
            System.out.println(img);
            Task task = new Task(title,img,href,"");
            weimeiinfo.getList().add(task);
        }
        list.add(weimeiinfo);

        Element elementBeauty= elementMain.select("div[class^=Sstyle2]").get(0);
        Elements beautyinfos = elementBeauty.select("li");
        BeautyInfo beautyinfo = new BeautyInfo();
        for (Element info : beautyinfos){
            Elements a = info.select("a");
            String title = a.select("img").attr("alt");
            String href = a.attr("href");
            String img = a.select("img").attr("src");
            System.out.println(title);
            System.out.println(href);
            System.out.println(img);
            Task task = new Task(title,img,href,"");
            beautyinfo.getList().add(task);
        }
        list.add(beautyinfo);

        Element elementHair= elementMain.select("div[class^=Sstyle2]").get(1);
        Elements hairinfos = elementHair.select("li");
        HairInfo hairinfo = new HairInfo();
        for (Element info : hairinfos){
            Elements a = info.select("a");
            String title = a.select("img").attr("alt");
            String href = a.attr("href");
            String img = a.select("img").attr("src");
            System.out.println(title);
            System.out.println(href);
            System.out.println(img);
            Task task = new Task(title,img,href,"");
            hairinfo.getList().add(task);
        }
        list.add(hairinfo);

        Elements elementSexy = elementMain.select("div[class^=Cstyle4]");
        Elements sexyinfos = elementSexy.select("li");
        SexyInfo sexyInfo = new SexyInfo();
        for (Element info : sexyinfos){
            Elements a = info.select("a");
            String title = a.select("img").attr("alt");
            String href = a.attr("href");
            String img = a.select("img").attr("src");
            System.out.println(title);
            System.out.println(href);
            System.out.println(img);
            Task task = new Task(title,img,href,"");
            sexyInfo.getList().add(task);
        }
        list.add(sexyInfo);

        return list;
    }
}
