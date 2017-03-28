package com.example.deng.experimentsix;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by deng on 2016/11/2.
 */
@TargetApi(Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
    private MusicService musicService;
    private Button playButton;
    private Button stopButton;
    private Button quitButton;
    private TextView statusText;
    private TextView curTime;
    private TextView totalTime;
    private ImageView imageView;
    private SeekBar seekBar;

    private Handler handler = new Handler();
    private Runnable myRun;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
    private Calendar calendar = Calendar.getInstance();
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder)service).getService();
            curTime.setText("00:00");
            calendar.setTimeInMillis(musicService.getTotalTime());
            totalTime.setText(dateFormat.format(calendar));
            seekBar.setMax(musicService.getTotalTime());

            myRun = new Runnable() {
                @Override
                public void run() {
                    if (musicService.getStatus() == MusicService.PLIYING) {
                        imageView.setRotation(imageView.getRotation() + 1);
                        playButton.setText("PAUSE");
                        statusText.setText("Playing");
                    } else if (musicService.getStatus() == MusicService.STOPPED) {
                        playButton.setText("PLAY");
                        statusText.setText("Stopped");
                    } else {
                        playButton.setText("PLAY");
                        statusText.setText("Paused");
                    }
                    seekBar.setProgress(musicService.getCurTime());
                    calendar.setTimeInMillis(musicService.getCurTime());
                    curTime.setText(dateFormat.format(calendar));
                    handler.postDelayed(this, 100);
                }
            };

            handler.post(myRun);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    private void findView() {
        playButton = (Button)findViewById(R.id.playButton);
        stopButton = (Button)findViewById(R.id.stopButton);
        quitButton = (Button)findViewById(R.id.quitButton);

        statusText = (TextView)findViewById(R.id.status);
        curTime = (TextView)findViewById(R.id.curTime);
        totalTime = (TextView)findViewById(R.id.totalTime);
        imageView = (ImageView)findViewById(R.id.album);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
    }

    private void bindButton() {
        // 绑定事件
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curText = playButton.getText().toString();
                if (musicService.getStatus() != MusicService.PLIYING) {
                    // 处于非播放状态
                    musicService.play();
                } else {
                    // 处于播放状态
                    musicService.pause();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.stop();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.stop();
                handler.removeCallbacks(myRun);
                unbindService(serviceConnection);
                MainActivity.this.finish();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                musicService.seekTo(seekBar.getProgress());
            }
        });

    }

    private void connection() {
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        findView();
        bindButton();
        connection();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
