package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.util.CacheUtil;
import com.lzx.listenmovieapp.util.SharedPrefsUtil;
import com.lzx.listenmovieapp.util.ToastUtil;

import butterknife.BindView;

/**
 * 设置
 *
 * @author cx
 */
public class SetActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.switch_wifi)
    Switch switch_wifi;

    @BindView(R.id.rl_cache)
    RelativeLayout rl_cache;

    @BindView(R.id.tv_cache)
    TextView tv_cache;

    @BindView(R.id.tv_about)
    TextView tv_about;

    @BindView(R.id.tv_recommend)
    TextView tv_recommend;

    @BindView(R.id.tv_feedback)
    TextView tv_feedback;

    @BindView(R.id.rl_update)
    RelativeLayout rl_update;

    boolean isOnlyWifi;
    String cacheSize;


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.ll_title)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initData() {
        isOnlyWifi = SharedPrefsUtil.getBoolean(this, "isOnlyWifi", true);
        try {
            cacheSize = CacheUtil.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {
        tv_title.setText("设置");
        switch_wifi.setChecked(isOnlyWifi);

        if (TextUtils.isEmpty(cacheSize)) {
            tv_cache.setText(cacheSize);
        }
    }

    @Override
    protected void setListener() {
        tv_back.setOnClickListener(this);
        rl_cache.setOnClickListener(this);
        tv_about.setOnClickListener(this);
        rl_update.setOnClickListener(this);
        tv_recommend.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);

        switch_wifi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

            } else {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.tv_back:
                finish();
                break;

            case R.id.rl_cache:
                CacheUtil.clearAllCache(this);
                try {
                    cacheSize = CacheUtil.getTotalCacheSize(this);
                    tv_cache.setText(cacheSize);
                    ToastUtil.show(this, "缓存已清理");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_about:
               startActivity(new Intent(this,AboutAppActivity.class));
                break;

            case R.id.rl_update:
                ToastUtil.show(this, "当前已是最新版本");
                break;

            case R.id.tv_recommend:
                startActivity(new Intent(this,RecommendActivity.class));
                break;

            case R.id.tv_feedback:
                startActivity(new Intent(this,FeedBackActivity.class));
                break;
        }
    }

}
