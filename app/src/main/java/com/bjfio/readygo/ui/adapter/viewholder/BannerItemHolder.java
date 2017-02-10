package com.bjfio.readygo.ui.adapter.viewholder;

import android.view.ViewGroup;

import com.bjfio.readygo.R;
import com.bjfio.readygo.model.bean.BannerInfo;
import com.bjfio.readygo.ui.view.BannerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class BannerItemHolder extends BaseViewHolder<BannerInfo> {
    BannerView bannerView;

    public BannerItemHolder(ViewGroup parent) {
        super(parent, R.layout.item_choice_banner);
        bannerView = $(R.id.header);
    }

    @Override
    public void setData(BannerInfo data) {
       bannerView.setData(data.getList());
    }
}
