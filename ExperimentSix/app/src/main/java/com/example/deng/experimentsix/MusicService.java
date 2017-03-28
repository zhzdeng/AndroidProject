package com.example.deng.experimentsix;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by deng on 2016/11/2.
 */
@TargetApi(Build.VERSION_CODES.N)
public class MusicService extends Service {
    static final int PLIYING = 0;
    static final int PAUSED = 1;
    static final int STOPPED = 2;

    private int status;
    private final IBinder binder = new MyBinder();
    private MediaPlayer mediaPlayer = new MediaPlayer();

    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mediaPlayer.setDataSource("data/betternottomeet.mp3");
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.setLooping(true);
        status = STOPPED;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    // 返回当前播放的时间数,整型
    public int getCurTime() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getTotalTime() {
        return mediaPlayer.getDuration();
    }

    public void stop() {
        if (status == STOPPED) return;
        mediaPlayer.pause();
        mediaPlayer.stop();
        try {
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        status = STOPPED;
    }

    public void play() {
        mediaPlayer.start();
        status = PLIYING;
    }

    public void pause() {
        mediaPlayer.pause();
        status = PAUSED;
    }

    public int getStatus() {
        return status;
    }

    public void seekTo(int arg) {
        mediaPlayer.seekTo(arg);
    }
}
