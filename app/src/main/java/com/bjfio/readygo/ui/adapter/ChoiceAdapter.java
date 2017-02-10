package com.bjfio.readygo.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.bjfio.readygo.model.bean.BannerInfo;
import com.bjfio.readygo.model.bean.BeautyInfo;
import com.bjfio.readygo.model.bean.HairInfo;
import com.bjfio.readygo.model.bean.RecentInfo;
import com.bjfio.readygo.model.bean.SexyInfo;
import com.bjfio.readygo.model.bean.WeiMeiInfo;
import com.bjfio.readygo.ui.adapter.viewholder.BannerItemHolder;
import com.bjfio.readygo.ui.adapter.viewholder.BeautyItemHolder;
import com.bjfio.readygo.ui.adapter.viewholder.HairItemHolder;
import com.bjfio.readygo.ui.adapter.viewholder.RecentItemHolder;
import com.bjfio.readygo.ui.adapter.viewholder.RtysItemHolder;
import com.bjfio.readygo.ui.adapter.viewholder.SexyItemHolder;
import com.bjfio.readygo.ui.adapter.viewholder.WeiMeiItemHolder;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.security.InvalidParameterException;

/**
 * Created by Administrator on 2017/2/8.
 */
public class ChoiceAdapter extends RecyclerArrayAdapter<Object> {
    public static final int TYPE_BANNER = 0;
    public static final int TYPE_RECENT = 1;
    public static final int TYPE_WEIMEI = 2;
    public static final int TYPE_BEAUTY = 3;
    public static final int TYPE_HAIR = 4;
    public static final int TYPE_SEXY = 5;
    public ChoiceAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        if(getItem(position) instanceof BannerInfo){
            return TYPE_BANNER;
        }else if (getItem(position) instanceof RecentInfo){
            return TYPE_RECENT;
        }else if (getItem(position) instanceof WeiMeiInfo){
            return TYPE_WEIMEI;
        }else if (getItem(position) instanceof BeautyInfo){
            return TYPE_BEAUTY;
        }else if (getItem(position) instanceof HairInfo){
            return TYPE_HAIR;
        }else if (getItem(position) instanceof SexyInfo){
            return TYPE_SEXY;
        }
        return 404;
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_BANNER:
                return new BannerItemHolder(parent);
            case TYPE_BEAUTY:
                return new BeautyItemHolder(parent);
            case TYPE_RECENT:
                return new RecentItemHolder(parent);
            case TYPE_WEIMEI:
                return new WeiMeiItemHolder(parent);
            case TYPE_HAIR:
                return new HairItemHolder(parent);
            case TYPE_SEXY:
                return new SexyItemHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }
}
