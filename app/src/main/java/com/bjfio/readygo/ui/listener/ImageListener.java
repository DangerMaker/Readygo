package com.bjfio.readygo.ui.listener;

import android.view.View;

import com.bjfio.readygo.model.bean.Task;
import com.bjfio.readygo.utils.JumpUtil;

/**
 * Created by liuxiaoyu on 2017/4/6.
 */

public class ImageListener implements View.OnClickListener{
    Task task;
    public ImageListener(Task task){
        this.task = task;
    }
    @Override
    public void onClick(View v) {
        JumpUtil.go2BrowseImagesActivity(v.getContext(), task.getUrl(),task.getImg());
    }
}
