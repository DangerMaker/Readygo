package com.bjfio.readygo.presenter;

import com.bjfio.readygo.base.RxPresenter;
import com.bjfio.readygo.model.net.RetrofitHelper;
import com.bjfio.readygo.presenter.contract.ScanImageContract;
import com.bjfio.readygo.utils.RxUtil;
import com.bjfio.readygo.utils.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/30.
 */
public class ScanImagePresenter extends RxPresenter implements ScanImageContract.Presenter {

    ScanImageContract.View mView;
    String url;

    public ScanImagePresenter(ScanImageContract.View mView,String url) {
        this.mView = StringUtils.checkNotNull(mView);
//        mView.setPresenter(this);
        this.url = url;
    }

    @Override
    public void getData() {
        Subscription rxSubscription = RetrofitHelper.getNewsApi().getDataFromUrl(url)
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        Document response = Jsoup.parse(s);
                        if (response != null) {
                            return RxUtil.createData(parseToImage(response));
                        }
                        return Observable.error(new Exception());
                    }
                })
                .compose(RxUtil.<String>rxSchedulerHelper())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(final String res) {
                        if (res != null) {
                            if (mView.isActive()) {
                                mView.showContent(res);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.refreshFaild(StringUtils.getErrorMsg(throwable.getMessage()));
                    }
                });
        addSubscrebe(rxSubscription);
    }

    private String parseToImage(Document document) {
        Elements elements = document.select("div[class^=articleBody]");
        Element imgElement = elements.get(0).select("img").first();
        String imgUrl = imgElement.attr("src");
        System.out.println("img -------->" + imgUrl);
//        Elements elements = document.select("div[class^=ArticleBox]");
//        Element imgElement = elements.get(0).select("img").first();
//        String imgUrl = imgElement.attr("src");
//        System.out.println("img -------->" + imgUrl);
        return imgUrl;
    }
}
