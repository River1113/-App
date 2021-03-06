package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.adapter.MovieListAdapter;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.MovieListInfo;
import com.lzx.listenmovieapp.http.Config;
import com.lzx.listenmovieapp.util.JsonLogUtil;
import com.lzx.listenmovieapp.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 设置
 *
 * @author cx
 */
public class MovieListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    private String title;
    private List<MovieListInfo> mData;
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
        return R.layout.activity_movie_list;
    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");

        mData = new ArrayList<>();
        getMovieList();

    }

    private void getMovieList() {
        changeRefreshStatus(true);
        HttpParams params = new HttpParams();
        //params.put("movieType", "movieType");//http://api.m.mtime.cn/PageSubArea/TrailerList.api
        //params.put("moveType",1);
        RxVolley.get(Config.MOVIE_LIST_URL, params, new HttpCallback() {//Config.MOVIE_LIST_URL
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                JsonLogUtil.log(result);

                List<MovieListInfo> list = JsonUtil.jsonToList(result, MovieListInfo.class);
                mData.addAll(list);
                mAdapter.notifyDataSetChanged();
                changeRefreshStatus(false);
            }

            @Override
            public void onFailure(VolleyError error) {
                super.onFailure(error);
                changeRefreshStatus(false);
            }


        });
    }

    @Override
    protected void initView() {
        tv_title.setText(title);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        initRecyclerView();
    }

    private void initRecyclerView() {
        rv_list.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MovieListAdapter(R.layout.item_movie_list, mData);
        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            //点击跳转到在线播放视频路径
            Intent intent = new Intent(MovieListActivity.this, OnlineMovieActivity.class);
            MovieListInfo info = mData.get(position);
            intent.putExtra("movieListInfo", info);
            startActivity(intent);
        });
        rv_list.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        tv_back.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    changeRefreshStatus(false);
                }
            }, 2000);
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
        }
    }

    private void changeRefreshStatus(boolean refreshing) {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(refreshing);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}
