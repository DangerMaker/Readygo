package com.bjfio.readygo.ui.adapter.viewholder;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjfio.readygo.R;
import com.bjfio.readygo.component.ImageLoader;
import com.bjfio.readygo.model.bean.HairInfo;
import com.bjfio.readygo.model.bean.SexyInfo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class SexyItemHolder extends BaseViewHolder<SexyInfo> {
    GridLayout gridLayout;
    TextView title;

    DisplayMetrics dm;

    public SexyItemHolder(ViewGroup parent) {
        super(parent, R.layout.item_choice_weimei);
        dm = getContext().getResources().getDisplayMetrics();
        gridLayout = $(R.id.gridlayout);
        title = $(R.id.title);
    }

    @Override
    public void setData(SexyInfo data) {
        gridLayout.removeAllViews();
        title.setText("高清套图");

        for (int i = 0; i < 6; i++) {
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task_style1, gridLayout,false);
            TextView picTitle = (TextView) convertView.findViewById(R.id.title);
            ImageView picImage = (ImageView) convertView.findViewById(R.id.img);

            ViewGroup.LayoutParams params = picImage.getLayoutParams();
            int width = dm.widthPixels/ 2;
            int height = width;
            params.width = width;
            params.height = height;
            picImage.setLayoutParams(params);

            picTitle.setText(data.getList().get(i).getTitle());
            ImageLoader.loadImage(getContext(), data.getList().get(i).getImg(), picImage);

            GridLayout.LayoutParams gl = (GridLayout.LayoutParams)convertView.getLayoutParams();
            gl.width = dm.widthPixels/ 2;
            gl.height = width;

            gridLayout.addView(convertView);
        }
    }
}
