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
import com.bjfio.readygo.model.bean.BeautyInfo;
import com.bjfio.readygo.utils.ScreenUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class BeautyItemHolder extends BaseViewHolder<BeautyInfo> {
    GridLayout gridLayout;
    TextView title;

    DisplayMetrics dm;

    public BeautyItemHolder(ViewGroup parent) {
        super(parent, R.layout.item_choice_beauty);
        dm = getContext().getResources().getDisplayMetrics();
        gridLayout = $(R.id.gridlayout);
        title = $(R.id.title);
    }

    @Override
    public void setData(BeautyInfo data) {
        gridLayout.removeAllViews();
        title.setText("美女图片");

        for (int i = 0; i < 9; i++) {
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task_style1, gridLayout,false);
            TextView picTitle = (TextView) convertView.findViewById(R.id.title);
            ImageView picImage = (ImageView) convertView.findViewById(R.id.img);

            ViewGroup.LayoutParams params = picImage.getLayoutParams();
            int width = dm.widthPixels/ 3;
            int height = (int) (width / 0.667);
            params.width = width;
            params.height = height;
            picImage.setLayoutParams(params);

            picTitle.setText(data.getList().get(i).getTitle());
            ImageLoader.loadImage(getContext(), data.getList().get(i).getImg(), picImage);

            GridLayout.LayoutParams gl = (GridLayout.LayoutParams)convertView.getLayoutParams();
            gl.width = dm.widthPixels/ 3;
            gl.height = (int)(width /0.667);

            gridLayout.addView(convertView);
        }
    }
}
