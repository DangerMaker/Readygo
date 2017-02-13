package com.bjfio.readygo.presenter;

import android.support.annotation.NonNull;

import com.bjfio.readygo.model.bean.Task;
import com.bjfio.readygo.model.net.RetrofitHelper;
import com.bjfio.readygo.presenter.contract.RtysListContract;
import com.bjfio.readygo.utils.RxUtil;
import com.bjfio.readygo.utils.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/2/10.
 */
public class SexyListPresenter extends RtysListPresenter {

    public SexyListPresenter(@NonNull RtysListContract.View view) {
        super(view);
    }

    @Override
    public void getData() {
        Subscription rxSubscription = RetrofitHelper.getImageApi().getSexyUrlPage(Integer.toString(page))
                .flatMap(new Func1<String, Observable<List<Task>>>() {
                    @Override
                    public Observable<List<Task>> call(String s) {
                        Document response = Jsoup.parse(s);
                        if (response != null) {
                            return RxUtil.createData(parse(response));
                        }
                        return Observable.error(new Exception());
                    }
                })
                .compose(RxUtil.<List<Task>>rxSchedulerHelper())
                .subscribe(new Action1<List<Task>>() {
                    @Override
                    public void call(final List<Task> res) {
                        if (res != null) {
                            if (mView.isActive()) {
                                if (page == 0) {
                                    mView.showContent(res);
                                } else {
                                    mView.showMoreContent(res);
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (page > 0) {
                            page--;
                        }
                        if (mView.isActive()) {
                            mView.refreshFaild(StringUtils.getErrorMsg(throwable.getMessage()));
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    public List<Task> parse(Document document) {
        List<Task> tasks = new ArrayList<>();
        Elements elements = document.select("div[class^=w960 r]");
        Element elementMain = elements.get(0);

        Elements elementLlist = elementMain.select("div[class^=listBox]");
        Elements liList = elementLlist.select("li");

        for (Element info : liList) {
            Elements a = info.select("a");
            String title = a.attr("title");
            String href = a.attr("href");
            String img = a.select("img").attr("src");
            System.out.println(title);
            System.out.println(href);
            System.out.println(img);
            Task task = new Task(title, img, href, "");
            tasks.add(task);
        }
        return tasks;
    }
}
