package com.lzx.listenmovieapp.ui;

import android.app.AlertDialog;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.MovieListInfo;
import com.lzx.listenmovieapp.download.StorageConfig;
import com.lzx.listenmovieapp.http.Config;
import com.lzx.listenmovieapp.util.FileSizeUtil;
import com.lzx.listenmovieapp.util.ToastUtil;

import java.io.File;

import butterknife.BindView;

/**
 * 作者：SuperLi on 2018/5/2 08:53
 * 邮箱：1092138112@qq.com
 */

public class OnlineMovieActivity extends BaseActivity {

    @BindView(R.id.simpleExoPlayerView)
    SimpleExoPlayerView simpleExoPlayerView;

    @BindView(R.id.iv_download)
    ImageView iv_download;

    @BindView(R.id.tv_progress)
    TextView tv_progress;

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
        movieName = info.getUrl().substring(info.getUrl().lastIndexOf("/")+1);//"摔跤吧爸爸.mp3"
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
        iv_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.iv_download:
                setDownLoad();
                break;
        }
    }

    private void setDownLoad() {
        File dir = StorageConfig.getVideoPath();
        Toast.makeText(this,dir.getAbsolutePath() + File.separator + movieName,Toast.LENGTH_LONG).show();
        RxVolley.download(dir.getAbsolutePath() + File.separator + movieName, url,
                (transferredBytes, totalSize) -> {
                    progressBar_download.setProgress((int) (transferredBytes * 100 / totalSize));
                    Log.e("transferredBytes: ", transferredBytes + "");
                    Log.e("totalSize: ", totalSize + "");
                    String _currentSize = FileSizeUtil.getFileFormatSize(transferredBytes);
                    String _totalSize = FileSizeUtil.getFileFormatSize(totalSize);
                    tv_progress.setText(_currentSize + "/" + _totalSize);
                },
                new HttpCallback() {
                    @Override
                    public void onFinish() {
                        super.onFinish();
                        ToastUtil.show(OnlineMovieActivity.this, "下载完成");
                        iv_download.setImageResource(R.mipmap.ic_download_finish);
                        tv_progress.setText("已下载");
                    }

                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        iv_download.setImageResource(R.mipmap.ic_download);
                        new AlertDialog.Builder(OnlineMovieActivity.this)
                                .setTitle(strMsg)
                                .setMessage("是否重新下载？")
                                .setPositiveButton("确定", (dialog, which) ->
                                        setDownLoad())
                                .setNegativeButton("取消", null)
                                .create()
                                .show();
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
