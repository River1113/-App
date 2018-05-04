package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.adapter.MovieDownLoadListAdapter;
import com.lzx.listenmovieapp.adapter.MovieListAdapter;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.MovieDownloadListInfo;
import com.lzx.listenmovieapp.utils.ToastUtil;

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
        String path = Environment.getExternalStorageDirectory() + "/mnt/sdcard/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        File[] listfiles = file.listFiles();
        mData = new ArrayList<>();
        for (int i = 0; i < listfiles.length; i++) {
            MovieDownloadListInfo info = new MovieDownloadListInfo();
            info.setImg("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1709857625,355767256&fm=179&w=115&h=161&img.JPEG");
            info.setName(listfiles[i].getName());
            info.setDesc("已下载");
            info.setScore(path + listfiles[i].getName());
            mData.add(info);
        }
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
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.show(DownLoadActivity.this, "选择播放第" + position + "个");
                Intent intent = new Intent(DownLoadActivity.this, LocalMovieActivity.class);
                intent.putExtra("path", mData.get(position).getScore());
                startActivity(intent);
            }
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

            case R.id.tv_right:
                ToastUtil.show(this, "管理");
                break;
        }
    }
}
