package com.lzx.listenmovieapp.ui;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import butterknife.BindView;
import me.wcy.lrcview.LrcView;

/**
 * 播放配音
 *
 * @author cx
 */
public class PlayDubbingActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.lrc_view)
    LrcView lrcView;

    @BindView(R.id.progress_bar)
    SeekBar seekBar;

    @BindView(R.id.btn_play_pause)
    Button btnPlayPause;

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Handler handler = new Handler();
    private File audio;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.ll_title)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_play_dubbing;
    }

    @Override
    protected void initData() {
        audio = (File) getIntent().getSerializableExtra("audio");
    }

    @Override
    protected void initView() {
        tv_title.setText("播放配音");
        configMedia();
    }

    private void configMedia() {
        try {
            mediaPlayer.reset();
//            AssetFileDescriptor fileDescriptor = getAssets().openFd("chengdu.mp3");

            mediaPlayer.setDataSource(audio.getAbsolutePath());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener((mp) -> {
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(0);
            });
            mediaPlayer.setOnCompletionListener((mp) -> {
                lrcView.updateTime(0);
                seekBar.setProgress(0);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        lrcView.loadLrc(getLrcText("成都.lrc"));

        lrcView.setOnPlayClickListener((time) -> {
            mediaPlayer.seekTo((int) time);
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                handler.post(runnable);
            }
            return true;
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
                mediaPlayer.seekTo(seekBar.getProgress());
                lrcView.updateTime(seekBar.getProgress());
            }
        });
    }

    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                long time = mediaPlayer.getCurrentPosition();
                lrcView.updateTime(time);
                seekBar.setProgress((int) time);
            }

            handler.postDelayed(this, 300);
        }
    };

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }

    @Override
    protected void setListener() {
        tv_back.setOnClickListener(this);
        btnPlayPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.tv_back:
                finish();
                break;

            case R.id.btn_play_pause:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    handler.post(runnable);
                } else {
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);
                }
                break;
        }
    }
}
