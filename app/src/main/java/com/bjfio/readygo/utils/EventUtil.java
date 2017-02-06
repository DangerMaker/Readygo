package com.bjfio.readygo.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;
import android.support.design.widget.Snackbar;


/**
 * Description:
 * Creator: yxc
 * date: $date $time
 */
public class EventUtil {
    protected static long lastClickTime;

    /**
     * 防止重复点击
     *
     * @return 是否重复点击
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int res) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, String msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.getView().setBackgroundColor(Color.RED);
        snackbar.show();
    }

    public static void showSnackbar(View view, int res) {
        Snackbar.make(view, res, Snackbar.LENGTH_SHORT).show();
    }
}
