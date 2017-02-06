package com.bjfio.readygo.ui.adapter.viewholder;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjfio.readygo.R;
import com.bjfio.readygo.component.ImageLoader;
import com.bjfio.readygo.model.bean.Task;
import com.bjfio.readygo.utils.ScreenUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class RtysItemHolder extends BaseViewHolder<Task> {
    ImageView image;
    TextView title;

    int column;
    public RtysItemHolder(ViewGroup parent,int column) {
        super(parent, R.layout.item_rtys);
        image = $(R.id.img);
        title = $(R.id.title);
        this.column = column;
    }

    @Override
    public void setData(Task data) {
        title.setText(data.getTitle());

        ViewGroup.LayoutParams params = image.getLayoutParams();
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels/ column;
        int height = (int) (width / 0.678);
        params.width = width;
        params.height = height;

        image.setLayoutParams(params);
        ImageLoader.load(getContext(),data.getImg(),image);
    }
}
