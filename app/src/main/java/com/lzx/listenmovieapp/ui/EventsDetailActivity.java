package com.lzx.listenmovieapp.ui;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.EventBean;

import butterknife.BindView;

/**
 * 设置
 *
 * @author cx
 */
public class EventsDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.iv_coverImage)
    ImageView iv_coverImage;

    @BindView(R.id.iv_status)
    ImageView iv_status;

    @BindView(R.id.tv_eventName)
    TextView tv_eventName;

    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_descriptionEmpty)
    TextView tv_descriptionEmpty;

    @BindView(R.id.tv_description)
    TextView tv_description;

    EventBean eventBean;


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.ll_title)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_event_detail;
    }

    @Override
    protected void initData() {
        eventBean = (EventBean) getIntent().getSerializableExtra("eventBean");
    }

    @Override
    protected void initView() {
        tv_title.setText("活动详情");
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        setDetailUI();
    }

    private void setDetailUI() {
        //设置封面图
        Glide.with(this).load(eventBean.getCoverImage()).placeholder(R.mipmap.image_placeholder).into(iv_coverImage);
        int status = eventBean.getStatus();
        //活动状态 (0.default,1.未开始,2.进行中，3.已结束)
        switch (status) {
            default:
                break;

            case 1:
                iv_status.setImageResource(R.mipmap.ic_event_nostart);
                iv_status.setVisibility(View.VISIBLE);
                break;

            case 2:
                iv_status.setImageResource(R.mipmap.ic_event_ongoing);
                iv_status.setVisibility(View.VISIBLE);
                break;

            case 3:
                iv_status.setImageResource(R.mipmap.ic_event_end);
                iv_status.setVisibility(View.VISIBLE);
                break;
        }

        //设置活动名称
        tv_eventName.setText(eventBean.getEventName());
        //设置时间
        String duration = eventBean.getStartTime() + " — " + eventBean.getEndTime();
        tv_time.setText(duration);
        //设置地址
        tv_address.setText(eventBean.getAddress());
        //设置描述
        String description = eventBean.getDescription();
        if (TextUtils.isEmpty(description)) {
            tv_descriptionEmpty.setVisibility(View.VISIBLE);
            tv_description.setVisibility(View.GONE);
        } else {
            tv_description.setText(description);
            tv_descriptionEmpty.setVisibility(View.GONE);
            tv_description.setVisibility(View.VISIBLE);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}
