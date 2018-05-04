package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.adapter.EventsAdapter;
import com.lzx.listenmovieapp.adapter.MovieListAdapter;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.EventBean;
import com.lzx.listenmovieapp.bean.MovieListInfo;
import com.lzx.listenmovieapp.utils.JsonUtil;
import com.lzx.listenmovieapp.utils.ToastUtil;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 活动专区
 *
 * @author cx
 */
public class EventsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    private List<EventBean> mData;
    private BaseQuickAdapter mAdapter;

    String jsonString = "[{\n" +
            "\t\"eventId\": 1,\n" +
            "\t\"eventName\": \"游玩\",\n" +
            "\t\"coverImage\": \"https://www.baidu.com/img/bd_logo1.png?where=super\",\n" +
            "\t\"status\": 3,\n" +
            "\t\"startTime\": \"2018/5/2\",\n" +
            "\t\"endTime\": \"2018/5/4\",\n" +
            "\t\"address\": \"济南\",\n" +
            "\t\"description\": \"玩的很有意义\"\n" +
            "}]";

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
        mData = new ArrayList<>();
        List<EventBean> list = JsonUtil.jsonToList(jsonString, EventBean.class);
        mData.addAll(list);
    }

    @Override
    protected void initView() {
        tv_title.setText("活动专区");
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        initRecyclerView();
    }

    private void initRecyclerView() {
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new EventsAdapter(R.layout.item_event, mData);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击跳转到在线播放视频路径
                Intent intent = new Intent(EventsActivity.this, EventsDetailActivity.class);
                intent.putExtra("eventBean", mData.get(position));
                startActivity(intent);
            }
        });
        rv_list.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        tv_back.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                        }
                    }
                }, 2000);
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
        }
    }
}
