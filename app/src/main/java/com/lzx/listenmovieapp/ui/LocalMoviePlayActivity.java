package com.lzx.listenmovieapp.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 作者：SuperLi on 2018/5/2 23:46
 * 邮箱：1092138112@qq.com
 */

public class LocalMoviePlayActivity extends BaseActivity {

    @BindView(R.id.sfv)
    SurfaceView sfv;

    @BindView(R.id.sb)
    SeekBar sb;

    @BindView(R.id.btn_play)
    Button btn_play;

    @BindView(R.id.btn_pause)
    Button btn_pause;

    private SurfaceHolder holder;
    private String path;
    private MediaPlayer player;
    private Timer timer;
    private TimerTask task;
    private int position = 0;

    private String filename;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_play_local_movie;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        holder = sfv.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void setListener() {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (player != null) {
                    player.seekTo(seekBar.getProgress());
                }
            }
        });

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                btn_play.setEnabled(true);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (player != null) {
                    position = player.getCurrentPosition();
                    stop();
                }
            }
        });
    }

    private void play() {
        btn_play.setEnabled(false);

        if (isPause) {
            isPause = false;
            player.start();
            return;
        }
        path = getIntent().getStringExtra("path");
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            player = new MediaPlayer();
            player.setDataSource(path);
            player.setDisplay(holder);

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    btn_play.setEnabled(true);
                    stop();
                }
            });

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d("zhangdi", "onPrepared");
                    sb.setMax(player.getDuration());
                    timer = new Timer();
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            if (player != null) {
                                int time = player.getCurrentPosition();
                                sb.setProgress(time);
                            }
                        }
                    };
                    timer.schedule(task, 0, 500);
                    sb.setProgress(position);
                    player.seekTo(position);
                    player.start();
                }
            });

            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(View v) {
        play();
        Log.d("zhangdi", path);
    }

    private boolean isPause;

    private void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
            isPause = true;
            btn_play.setEnabled(true);
        }
    }

    public void pause(View v) {
        pause();
    }

    private void replay() {
        isPause = false;
        if (player != null) {
            stop();
            play();
        }
    }

    public void replay(View v) {
        replay();
    }

    private void stop() {
        isPause = false;
        if (player != null) {
            sb.setProgress(0);
            player.stop();
            player.release();
            player = null;
            if (timer != null) {
                timer.cancel();
            }
            btn_play.setEnabled(true);
        }
    }

    public void stop(View v) {
        stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }

    @Override
    public void onClick(View v) {

    }
}
