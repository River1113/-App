package com.lzx.listenmovieapp.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.utils.ToastUtil;

import butterknife.BindView;

/**
 * 远程点播
 *
 * @author cx
 */
public class RemotePlayedActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.iv_voice)
    ImageView iv_voice;

    @BindView(R.id.btn_send)
    Button btn_send;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.ll_title)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_remote_player;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tv_title.setText("远程点播");
    }

    @Override
    protected void setListener() {
        tv_back.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        iv_voice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.tv_back:
                finish();
                break;

            case R.id.btn_send:
                ToastUtil.show(this, "发送");
                break;

            case R.id.iv_voice:
                ToastUtil.show(this, "开始语音");
                break;
        }
    }
}
