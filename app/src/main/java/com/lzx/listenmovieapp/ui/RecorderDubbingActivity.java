package com.lzx.listenmovieapp.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.record.AudioRecorder;
import com.lzx.listenmovieapp.util.ToastUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import me.wcy.lrcview.LrcView;

/**
 * Author：Road
 * Date  ：2018/5/16
 */

public class RecorderDubbingActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.lrc_view)
    LrcView lrcView;

    @BindView(R.id.btn_play_pause)
    Button btnPlayPause;

    AudioRecorder audioRecorder;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.ll_title)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_recorder_dubbing;
    }

    @Override
    protected void initData() {
        audioRecorder = AudioRecorder.getInstance();
    }

    @Override
    protected void initView() {
        tv_title.setText("开始配音");

        lrcView.loadLrc(getLrcText("成都.lrc"));
        lrcView.setOnPlayClickListener(new LrcView.OnPlayClickListener() {
            @Override
            public boolean onPlayClick(long time) {
                return true;
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
                try {
                    if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_NO_READY) {
                        permissionReq();
                    } else {
                        //停止录音
                        audioRecorder.stopRecord();
                        btnPlayPause.setText("开始录音");
                        ToastUtil.show(this, "配音已保存,打开>我的录音<即可查看");
                        finish();
                    }

                } catch (IllegalStateException e) {
                    ToastUtil.show(this, e.getMessage());
                }
                break;
        }
    }

    private void permissionReq() {
        AndPermission.with(this)
                .requestCode(1)
                .permission(Manifest.permission.RECORD_AUDIO)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        //初始化录音
                        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                        audioRecorder.createDefaultAudio(fileName);
                        audioRecorder.startRecord(null);
                        btnPlayPause.setText("保存配音");
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        new AlertDialog.Builder(RecorderDubbingActivity.this)
                                .setMessage("存储权限被拒绝，需要开启")
                                .setPositiveButton("确定", (dialogInterface, i) -> permissionReq())
                                .create()
                                .show();
                    }
                })
                .start();
    }

    @Override
    protected void onDestroy() {
        audioRecorder.release();
        super.onDestroy();
    }
}
