package com.lzx.listenmovieapp.ui;

import android.content.Intent;
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
import com.lzx.listenmovieapp.bean.MovieListInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 最近播放
 *
 * @author cx
 */
public class RecentlyPlayedActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    List<MovieListInfo> mData = new ArrayList<>();
    BaseQuickAdapter mAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.ll_title)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MovieListInfo info = new MovieListInfo();
            info.setImg("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1709857625,355767256&fm=179&w=115&h=161&img.JPEG");
            info.setName("战狼");
            info.setDesc("在南疆围捕贩毒分子的行动中,特种部队狙击手冷锋(吴京 饰)公然违抗上级的命令,开枪射杀伤害了战友的暴徒...");
            info.setScore("");
            mData.add(info);
        }
    }

    @Override
    protected void initView() {
        tv_title.setText("最近播放");
        initRecyclerView();
    }

    private void initRecyclerView() {
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MovieDownLoadListAdapter(R.layout.item_download_list, mData);
        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            //点击跳转到在线播放视频路径
            Intent intent = new Intent(RecentlyPlayedActivity.this, OnlineMovieActivity.class);
            MovieListInfo info = mData.get(position);
            intent.putExtra("movieListInfo", info);
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
