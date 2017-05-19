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
import com.bjfio.readygo.model.bean.HairInfo;
import com.bjfio.readygo.model.bean.SexyInfo;
import com.bjfio.readygo.model.bean.Task;
import com.bjfio.readygo.ui.activitys.ImageListActivity;
import com.bjfio.readygo.ui.fragments.ImageListFragment;
import com.bjfio.readygo.ui.listener.ImageListener;
import com.bjfio.readygo.utils.ScreenUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class SexyItemHolder extends BaseViewHolder<SexyInfo> {
    GridLayout gridLayout;
    TextView title;
    TextView more;
    DisplayMetrics dm;
    int padding = 4;
    int margin;

    public SexyItemHolder(ViewGroup parent) {
        super(parent, R.layout.item_choice_weimei);
        dm = getContext().getResources().getDisplayMetrics();
        gridLayout = $(R.id.gridlayout);
        title = $(R.id.title);
        more = $(R.id.more);
        margin = ScreenUtil.dip2px(getContext(),padding);
    }

    @Override
    public void setData(SexyInfo data) {
        gridLayout.removeAllViews();
        title.setText("高清套图");

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImageListActivity.class);
                intent.putExtra(ImageListFragment.ARGUMENT_TYPE,ImageListFragment.TYPE_SEXY);
                getContext().startActivity(intent);
            }
        });

        for (int i = 0; i < 6; i++) {
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task_style1, gridLayout,false);
            TextView picTitle = (TextView) convertView.findViewById(R.id.title);
            ImageView picImage = (ImageView) convertView.findViewById(R.id.img);

            ViewGroup.LayoutParams params = picImage.getLayoutParams();
            int width = (dm.widthPixels - 3 * margin) / 2;
            int height = width;
            params.width = width;
            params.height = height;
            picImage.setLayoutParams(params);

            picTitle.setText(data.getList().get(i).getTitle());
            ImageLoader.loadImage(getContext(), data.getList().get(i).getImg(), picImage);
            setImageListener(picImage,data.getList().get(i));

            GridLayout.LayoutParams gl = (GridLayout.LayoutParams)convertView.getLayoutParams();
            gl.width = (dm.widthPixels - 3 * margin) / 2;
            gl.height = width;
            gl.setMargins(margin / 2, margin / 2, margin /2, margin / 2);

            gridLayout.addView(convertView);
        }
    }

    private void setImageListener(View view, Task task){
        view.setOnClickListener(new ImageListener(task));
    }
}
