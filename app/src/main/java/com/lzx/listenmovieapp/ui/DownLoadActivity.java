package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.adapter.MovieDownLoadListAdapter;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.MovieDownloadListInfo;
import com.lzx.listenmovieapp.download.DownloadConfig;
import com.lzx.listenmovieapp.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 下载电影
 *
 * @author cx
 */
public class DownLoadActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.ll_localMovies)
    LinearLayout ll_localMovies;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    private List<MovieDownloadListInfo> mData;
    private BaseQuickAdapter mAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.ll_title)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
    }

    @Override
    protected void initView() {
        tv_title.setText("下载电影");
        tv_right.setText("管理");
        tv_right.setVisibility(View.VISIBLE);

        initRecyclerView();
    }

    private void initRecyclerView() {
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MovieDownLoadListAdapter(R.layout.item_download_list, mData);
        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
           
        });
        rv_list.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        tv_back.setOnClickListener(this);
        ll_localMovies.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.tv_back:
                finish();
                break;

            case R.id.tv_right:
                ToastUtil.show(this, "管理");
                break;

            case R.id.ll_localMovies:
                startActivity(new Intent(this, LocalMoviesActivity.class));
                break;
        }
    }
}
