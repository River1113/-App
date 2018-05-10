package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.adapter.HomeAdapter;
import com.lzx.listenmovieapp.adapter.MovieDownLoadListAdapter;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.HomeItem;
import com.lzx.listenmovieapp.bean.MovieDownloadListInfo;
import com.lzx.listenmovieapp.download.DownloadConfig;
import com.lzx.listenmovieapp.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 影库资源
 *
 * @author cx
 */
public class LocalMoviesActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

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
        return R.layout.activity_source;
    }

    @Override
    protected void initData() {
        File file = DownloadConfig.getFilePath();
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        mData = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            MovieDownloadListInfo info = new MovieDownloadListInfo();
            info.setImg("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1709857625,355767256&fm=179&w=115&h=161&img.JPEG");
            info.setName(files[i].getName());
            info.setDesc("已下载");
            info.setScore(file.getAbsolutePath() + files[i].getName());
            mData.add(info);
        }
    }

    @Override
    protected void initView() {
        tv_title.setText("缓存电影");
        initRecyclerView();
    }

    private void initRecyclerView() {
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MovieDownLoadListAdapter(R.layout.item_download_list, mData);
        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            ToastUtil.show(LocalMoviesActivity.this, "选择播放第" + position + "个");
            Intent intent = new Intent(LocalMoviesActivity.this, LocalMoviePlayActivity.class);
            intent.putExtra("path", mData.get(position).getScore());
            startActivity(intent);
        });
        rv_list.setAdapter(mAdapter);
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
