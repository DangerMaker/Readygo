package com.bjfio.readygo.presenter.contract;

import com.bjfio.readygo.base.BasePresenter;
import com.bjfio.readygo.base.BaseView;
import com.bjfio.readygo.model.bean.Task;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
public class ScanImageContract {

    public interface View extends BaseView<Presenter> {

        boolean isActive();

        void showContent(String response);

        void refreshFaild(String msg);
    }

    public interface Presenter extends BasePresenter {
        void getData();
    }
}
