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

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：SuperLi on 2018/5/2 23:46
 * 邮箱：1092138112@qq.com
 */

public class LocalMovieActivity extends AppCompatActivity{
   private SurfaceView sfv;
   private SeekBar sb;
   private String path ;
   private SurfaceHolder holder;
   private MediaPlayer player;
   private Button Play;
   private Timer timer;
   private TimerTask task;
   private int position = 0;
   //private EditText et;

   private String filename;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.layout_localmovie);
      initView();
   }


   private void initView() {
      sfv = (SurfaceView) findViewById(R.id.sfv);
      sb = (SeekBar) findViewById(R.id.sb);
      Play = (Button) findViewById(R.id.play);
      //et = (EditText) findViewById(R.id.et);
      Play.setEnabled(false);

      holder = sfv.getHolder();
      holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

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
            Log.d("zhangdi","surfaceCreated");
            Play.setEnabled(true);
         }

         @Override
         public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d("zhangdi","surfaceChanged");
         }

         @Override
         public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d("zhangdi","surfaceDestroyed");
            if (player != null) {
               position = player.getCurrentPosition();
               stop();
            }
         }
      });
   }

   private void play() {

      Play.setEnabled(false);

      if (isPause) {
         isPause = false;
         player.start();
         return;
      }
      path = getIntent().getStringExtra("path");
      File file = new File(path);
      if (!file.exists()) {
         Toast.makeText(this,"文件不存在",Toast.LENGTH_LONG).show();
         return;
      }

      try {
         player = new MediaPlayer();
         player.setDataSource(path);
         player.setDisplay(holder);

         player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
               Play.setEnabled(true);
               stop();
            }
         });

         player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
               Log.d("zhangdi","onPrepared");
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
               timer.schedule(task,0,500);
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
      Log.d("zhangdi",path);
   }

   private boolean isPause;
   private void pause() {
      if (player != null && player.isPlaying()) {
         player.pause();
         isPause = true;
         Play.setEnabled(true);
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

   private void stop(){
      isPause = false;
      if (player != null) {
         sb.setProgress(0);
         player.stop();
         player.release();
         player = null;
         if (timer != null) {
            timer.cancel();
         }
         Play.setEnabled(true);
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
}
