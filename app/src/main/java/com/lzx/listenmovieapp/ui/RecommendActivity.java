package com.lzx.listenmovieapp.ui;

import android.view.View;
import android.widget.TextView;

import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;

import butterknife.BindView;

/**
 * 推荐给朋友
 *
 * @author cx
 */
public class RecommendActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.ll_title)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_recommend;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tv_title.setText("推荐");
    }

    @Override
    protected void setListener() {
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.tv_back:
                finish();
                break;
        }
    }
}
