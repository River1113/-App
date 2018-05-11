package com.lzx.listenmovieapp.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author：Road
 * Date  ：2018/4/26
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        //绑定控件
        unbinder = ButterKnife.bind(this);

        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }

        //初始化数据
        initData();
        //view与数据绑定
        initView();
        //设置监听
        setListener();
    }

    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(false);
        mImmersionBar.flymeOSStatusBarFontColorInt(Color.WHITE);
        mImmersionBar.init();
    }

    /**
     * 设置LayoutId
     *
     * @return layoutId
     */
    protected abstract int setLayoutId();

    /**
     * 初始化数据源
     */
    protected abstract void initData();


    /**
     * 初始化控件
     */
    protected abstract void initView();


    /**
     * 设置监听
     */
    protected abstract void setListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //在BaseActivity里销毁
        }
    }
}
