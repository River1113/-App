package com.lzx.listenmovieapp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.MovieListInfo;
import com.lzx.listenmovieapp.download.DownloadConfig;
import com.lzx.listenmovieapp.http.Config;

import java.io.File;

import butterknife.BindView;

/**
 * 作者：SuperLi on 2018/5/2 08:53
 * 邮箱：1092138112@qq.com
 */

public class OnlineMovieActivity extends BaseActivity {

    @BindView(R.id.simpleExoPlayerView)
    SimpleExoPlayerView simpleExoPlayerView;

    @BindView(R.id.btn_download)
    Button btn_download;

    @BindView(R.id.rl_download_progress)
    RelativeLayout rl_download_progress;

    @BindView(R.id.progressBar_download)
    ProgressBar progressBar_download;


    SimpleExoPlayer player;
    MovieListInfo info;
    String url = "";
    String movieName;
    long fileSize;
    MediaSource videoSource;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.rl_play)
                .statusBarColor(R.color.txtMainColor)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.layout_onlinemovie;
    }

    @Override
    protected void initData() {
        info = (MovieListInfo) getIntent().getSerializableExtra("movieListInfo");
        if (info == null) {
            return;
        }
        url = Config.RESOURCE_URL + info.getUrl();
        fileSize = info.getSize();
        movieName = info.getUrl();
    }

    @Override
    protected void initView() {
        play();
    }

    private void play() {
        // 1.创建一个默认TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        // 2.创建一个默认的LoadControl
        LoadControl loadControl = new DefaultLoadControl();

        // 3.创建播放器
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        // 将player关联到View上
        simpleExoPlayerView.setPlayer(player);

        DefaultBandwidthMeter bandwidthMeter2 = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "yourApplicationName"), bandwidthMeter2);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        if (url.contains(".m3u8")) {
            videoSource = new HlsMediaSource(Uri.parse(url), dataSourceFactory, null, null);
        } else {
            //test mp4
            videoSource = new ExtractorMediaSource(Uri.parse(url),
                    dataSourceFactory, extractorsFactory, null, null);
        }

        player.prepare(videoSource);
    }

    @Override
    protected void setListener() {
        btn_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.btn_download:
                setDownLoad(url);
                break;
        }
    }

    private void setDownLoad(String appDownload) {
        File dir = DownloadConfig.getFilePath();
        FileDownloader.getImpl()
                .create(appDownload)
                .setPath(dir.getAbsolutePath() + File.separator + movieName)
                .setForceReDownload(true)
                .setListener(fileDownloadListener)
                .start();
    }

    FileDownloadListener fileDownloadListener = new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        }

        @Override
        protected void started(BaseDownloadTask task) {
            super.started(task);
            btn_download.setVisibility(View.GONE);
            rl_download_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            if (fileSize != 0) {
                progressBar_download.setProgress(soFarBytes / 1024 / 1024);
            }
            Log.e("soFarBytes", "progress: " + soFarBytes);
        }

        @Override
        protected void blockComplete(BaseDownloadTask task) {
        }

        @Override
        protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            File apkFile = new File(task.getPath());

            //判读版本是否在7.0以上
            if (Build.VERSION.SDK_INT >= 24) {
                Uri apkUri = FileProvider.getUriForFile(OnlineMovieActivity.this, getPackageName() + ".fileprovider", apkFile);
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                startActivity(install);
            } else {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(install);
            }
            OnlineMovieActivity.this.finish();
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            new AlertDialog.Builder(OnlineMovieActivity.this)
                    .setTitle("下载出错")
                    .setMessage("是否重新下载？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setDownLoad(url);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            OnlineMovieActivity.this.finish();
                        }
                    })
                    .create()
                    .show();
        }

        @Override
        protected void warn(BaseDownloadTask task) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileDownloader.getImpl().pause(fileDownloadListener);
        player.release();
    }
}
