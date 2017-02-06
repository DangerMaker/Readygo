package com.bjfio.readygo.utils;

import android.app.Activity;
import android.widget.Toast;

import com.bjfio.readygo.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;


/**
 * Created by dingwei on 16/6/7.
 * 分享类
 */
public class UMShareTools {
    private static Activity activity;
    final static SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
            {
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA
            };
    private static String sharecontent;//分享内容
    private static String sharetitle;//分享标题
    private static String shareurl;//分享地址
    private static String shareimgurl;//图片地址

    public static void share(Activity context,String content,String title,String imgurl,String url){
        shareimgurl=imgurl;
        activity=context;
        sharecontent=content;
        sharetitle=title;
        shareurl=url;

        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        config.setCancelButtonVisibility(false);

        new ShareAction(context).setDisplayList(displaylist )
                .setListenerList(umShareListener)
                .setShareboardclickCallback(shareBoardlistener)
                .open(config);
    }
    private static ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            UMImage umimage;
            if(shareimgurl==null||shareimgurl.equals("")){
                umimage=new UMImage(activity, R.mipmap.ic_launcher);
            }else{
                umimage=new UMImage(activity, shareimgurl);
            }
            if(!share_media.equals(SHARE_MEDIA.SINA)){
                new ShareAction(activity).setPlatform(share_media).setCallback(umShareListener)
                        .withTitle(sharetitle)
                        .withText(sharecontent)
                        .withMedia(umimage)
                        .withTargetUrl(shareurl)
                        .share();
            }else{
                new ShareAction(activity).setPlatform(share_media).setCallback(umShareListener)
                        .withText(sharecontent+shareurl)
                        .withMedia(umimage)
                        .share();
            }

        }
    };
    private static UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(activity, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(activity,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(activity,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
