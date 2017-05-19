package com.bjfio.readygo.ui.activitys;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.bjfio.readygo.R;
import com.bjfio.readygo.base.BaseActivity;
import com.bjfio.readygo.ui.view.MyVideoView;

import butterknife.BindView;

/**
 * Created by liuxiaoyu on 2017/5/13.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.videoView)
    MyVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void initView() {
        final Uri videoPath = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.start);
        videoView.setVideoURI(videoPath);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }});
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoURI(videoPath);
                videoView.start();
            }
        });
    }
}
