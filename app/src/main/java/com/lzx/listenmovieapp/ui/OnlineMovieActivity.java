package com.lzx.listenmovieapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import com.lzx.listenmovieapp.R;

/**
 * 作者：SuperLi on 2018/5/2 08:53
 * 邮箱：1092138112@qq.com
 */

public class OnlineMovieActivity extends AppCompatActivity {

   private String url="";
   private Context context;
   private TrackSelection.Factory videoTrackSelectionFactory;
   private Handler mainHandler = new Handler();
   private MediaSource videoSource;
   private SimpleExoPlayer player ;
   public void start(View v){

   }
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      context=OnlineMovieActivity.this;

      setContentView(R.layout.layout_onlinemovie);
      // 1.创建一个默认TrackSelector
      BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
      TrackSelection.Factory videoTrackSelectionFactory =
         new AdaptiveTrackSelection.Factory(bandwidthMeter);
      TrackSelector trackSelector =
         new DefaultTrackSelector(videoTrackSelectionFactory);
      // 2.创建一个默认的LoadControl
      LoadControl loadControl = new DefaultLoadControl();

      // 3.创建播放器
      player = ExoPlayerFactory.newSimpleInstance(context,trackSelector,loadControl);
      SimpleExoPlayerView simpleExoPlayerView= (SimpleExoPlayerView) findViewById(R.id.simpleExoPlayerView);
      // 将player关联到View上
      simpleExoPlayerView.setPlayer(player);


      DefaultBandwidthMeter bandwidthMeter2 = new DefaultBandwidthMeter();
// Produces DataSource instances through which media data is loaded.
      DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
         Util.getUserAgent(context, "yourApplicationName"), bandwidthMeter2);
      ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
      //url="http://mpv.videocc.net/ce0812b122/a/ce0812b122bf0fb49d79ebd97cbe98fa_1.mp4";
      // test hls
      //url="http://hls.videocc.net/ce0812b122/c/ce0812b122c492470605bc47d3388a09_3.m3u8";
      url=getIntent().getStringExtra("url");
      url="http://hls.videocc.net/ce0812b122/c/ce0812b122c492470605bc47d3388a09_3.m3u8";
      Toast.makeText(this,url,Toast.LENGTH_LONG).show();
      if(url.contains(".m3u8")){
         videoSource =new HlsMediaSource(Uri.parse(url),dataSourceFactory,null,null);
      }else{
         //test mp4
         videoSource = new ExtractorMediaSource(Uri.parse(url),
            dataSourceFactory, extractorsFactory, null, null);
      }

      player.prepare(videoSource);
// Prepare the player with the source.

   }

   @Override
   protected void onDestroy() {
      super.onDestroy();
      player.release();
   }
}
