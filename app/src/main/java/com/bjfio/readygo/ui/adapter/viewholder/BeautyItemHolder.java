package com.bjfio.readygo.ui.adapter.viewholder;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjfio.readygo.R;
import com.bjfio.readygo.component.ImageLoader;
import com.bjfio.readygo.event.HomeTabEvent;
import com.bjfio.readygo.model.bean.BeautyInfo;
import com.bjfio.readygo.ui.activitys.ImageListActivity;
import com.bjfio.readygo.ui.fragments.ImageListFragment;
import com.bjfio.readygo.utils.ScreenUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import org.simple.eventbus.EventBus;

public class BeautyItemHolder extends BaseViewHolder<BeautyInfo> {
    GridLayout gridLayout;
    TextView title;
    TextView more;

    DisplayMetrics dm;
    int padding = 4;
    int margin;

    public BeautyItemHolder(ViewGroup parent) {
        super(parent, R.layout.item_choice_beauty);
        dm = getContext().getResources().getDisplayMetrics();
        gridLayout = $(R.id.gridlayout);
        title = $(R.id.title);
        more = $(R.id.more);
        margin = ScreenUtil.dip2px(getContext(),padding);
    }

    @Override
    public void setData(BeautyInfo data) {
        gridLayout.removeAllViews();
        title.setText("美女图片");
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ImageListActivity.class);
//                intent.putExtra(ImageListFragment.ARGUMENT_TYPE,ImageListFragment.TYPE_SEXY);
//                getContext().startActivity(intent);
            }
        });

        for (int i = 0; i < 9; i++) {
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task_style1, gridLayout,false);
            TextView picTitle = (TextView) convertView.findViewById(R.id.title);
            ImageView picImage = (ImageView) convertView.findViewById(R.id.img);

            ViewGroup.LayoutParams params = picImage.getLayoutParams();
            int width = (dm.widthPixels - 4 * margin) / 3;
            int height = (int) (width / 0.667);
            params.width = width;
            params.height = height;
            picImage.setLayoutParams(params);

            picTitle.setText(data.getList().get(i).getTitle());
            ImageLoader.loadImage(getContext(), data.getList().get(i).getImg(), picImage);

            GridLayout.LayoutParams gl = (GridLayout.LayoutParams)convertView.getLayoutParams();
            gl.width = (dm.widthPixels - 4 * margin) / 3;
            gl.height = (int)(width /0.667);
            gl.setMargins(margin / 2, margin / 2, margin /2, margin / 2);

            gridLayout.addView(convertView);
        }
    }
}
