package com.bjfio.readygo.utils;

import android.content.Context;
import android.content.Intent;

import com.bjfio.readygo.ui.activitys.BrowseImagesActivity;

/**
 * Description: JumpUtil
 * Creator: yxc
 * date: 2016/9/21 14:46
 */
public class JumpUtil {

    public static void go2BrowseImagesActivity(Context context, String url,String imageUrl) {
        Intent intent = new Intent(context, BrowseImagesActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("imageUrl",imageUrl);
        context.startActivity(intent);
    }

    private static void jump(Context a, Class<?> clazz) {
        Intent intent = new Intent(a, clazz);
        a.startActivity(intent);
    }
}
