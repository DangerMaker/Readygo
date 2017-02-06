package com.bjfio.readygo.presenter;

import android.support.annotation.NonNull;

import com.bjfio.readygo.base.RxPresenter;
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
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/17.
 */
public class RtysListPresenter extends RxPresenter implements RtysListContract.Presenter {

    RtysListContract.View mView;
    int page = 1;

    public RtysListPresenter(@NonNull RtysListContract.View view) {
        mView = StringUtils.checkNotNull(view);
//        mView.setPresenter(this);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData();
    }

    @Override
    public void loadMore() {
        page++;
        getData();
    }

    public void getData() {
        Subscription rxSubscription = RetrofitHelper.getNewsApi().getHtml(Integer.toString(page))
                .flatMap(new Func1<String, Observable<List<Task>>>() {
                    @Override
                    public Observable<List<Task>> call(String s) {
                        Document response = Jsoup.parse(s);
                        if (response != null) {
                            return RxUtil.createData(parseToRtys(response));
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

    private List<Task> parseToRtys(Document document) {
        Elements elements = document.select("div[class^=libox]");
        Iterator<Element> iterator = elements.iterator();
        int i = 0;

        while (iterator.hasNext()) {
            if (i < 8) {
                iterator.next();
                iterator.remove();
                i++;
            } else {
                break;
            }
        }

        List<Task> tasks = new ArrayList<>();
        for (Element element : elements) {
            Element imgElement = element.select("img").first();
            if (imgElement == null) {
                continue;
            }
            String imgUrl = imgElement.attr("lazysrc");
            System.out.println("img -------->" + imgUrl);

            Elements titleElement = element.select("p");
            String title = titleElement.text();
            System.out.println("title ------->" + title);

            Elements hrefElement = element.select("a");
            String href = hrefElement.attr("href");
            System.out.println("href ------->" + href);

            Task task = new Task(title, imgUrl, href, "");
            tasks.add(task);
        }
        return tasks;
    }
}
