package com.bjfio.readygo.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bjfio.readygo.R;
import com.bjfio.readygo.app.App;
import com.bjfio.readygo.utils.KL;
import com.bjfio.readygo.utils.PreUtils;
import com.bjfio.readygo.utils.ScreenUtil;
import com.bjfio.readygo.utils.SystemUtils;
import com.bjfio.readygo.widget.theme.Theme;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Description: BaseActivity
 * Creator: yxc
 * date: 2016/9/7 11:45
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity {

    protected final String TAG = getClass().getSimpleName();
    protected boolean isConnection = false;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KL.d(this.getClass(), this.getClass().getName() + "------>onCreate");
        setTranslucentStatus(true);
        onPreCreate();
        isConnection = SystemUtils.checkNet(this);
        regReceiver();
        App.getInstance().registerActivity(this);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        KL.d(this.getClass(), this.getClass().getName() + "------>onStart");
//        setTitleHeight(getRootView(this));
        setToolBarPaddingTop(getRootView(this));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        KL.d(this.getClass(), this.getClass().getName() + "------>onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        KL.d(this.getClass(), this.getClass().getName() + "------>onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        KL.d(this.getClass(), this.getClass().getName() + "------>onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        KL.d(this.getClass(), this.getClass().getName() + "------>onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KL.d(this.getClass(), this.getClass().getName() + "------>onDestroy");
        App.getInstance().unregisterActivity(this);
        if (netListener != null)
            unregisterReceiver(netListener);
        if (unbinder != null)
            unbinder.unbind();
    }

    private void onPreCreate() {
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {
            case Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Red:
                setTheme(R.style.RedTheme);
                break;
            case Brown:
                setTheme(R.style.BrownTheme);
                break;
            case Green:
                setTheme(R.style.GreenTheme);
                break;
            case Purple:
                setTheme(R.style.PurpleTheme);
                break;
            case Teal:
                setTheme(R.style.TealTheme);
                break;
            case Pink:
                setTheme(R.style.PinkTheme);
                break;
            case DeepPurple:
                setTheme(R.style.DeepPurpleTheme);
                break;
            case Orange:
                setTheme(R.style.OrangeTheme);
                break;
            case Indigo:
                setTheme(R.style.IndigoTheme);
                break;
            case LightGreen:
                setTheme(R.style.LightGreenTheme);
                break;
            case Lime:
                setTheme(R.style.LimeTheme);
                break;
            case DeepOrange:
                setTheme(R.style.DeepOrangeTheme);
                break;
            case Cyan:
                setTheme(R.style.CyanTheme);
                break;
            case BlueGrey:
                setTheme(R.style.BlueGreyTheme);
                break;
            case Black:
                setTheme(R.style.BlackTheme);
                break;
        }

    }

    protected void initView() {
    }

    protected void initEvent() {
    }

    /**
     * 注册广播，监听网络状态
     */
    private void regReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(netListener, filter);
    }

    private BroadcastReceiver netListener = new BroadcastReceiver() {

        String wifiAction = "android.net.wifi.WIFI_STATE_CHANGED";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && action.equals(wifiAction)) {
                isConnection = SystemUtils.checkNet(context);
            }
        }
    };

    /**
     * 设置沉浸式
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    private void setToolBarPaddingTop(View view) {
        if (view != null) {
            View toolbar = view.findViewById(R.id.toolbar);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                if (toolbar != null) {
                    ViewGroup.LayoutParams lp = toolbar.getLayoutParams();
                    lp.height = ScreenUtil.dip2px(this, 68);
                    toolbar.setLayoutParams(lp);
                    toolbar.setPadding(0, getDimensionMiss(), 0, 0);
                }
            }
        }
    }

    protected int getDimensionMiss() {
        Resources resources = BaseActivity.this.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    protected static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }
}
