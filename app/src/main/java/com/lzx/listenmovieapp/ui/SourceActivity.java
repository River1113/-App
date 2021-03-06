package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.adapter.HomeAdapter;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.HomeItem;
import com.lzx.listenmovieapp.util.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 影库资源
 *
 * @author cx
 */
public class SourceActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    @BindView(R.id.banner)
    Banner banner;

    public static final String[] TITLES = {"动作",
            "冒险",
            "喜剧",
            "爱情",
            "战争",
            "恐怖",
            "科幻",
            "悬疑",
            "其他",
    };

    public static final int[] IMAGES = {

            R.mipmap.action,
            R.mipmap.risk,
            R.mipmap.interest,
            R.mipmap.love,
            R.mipmap.war,
            R.mipmap.terror,
            R.mipmap.sience,
            R.mipmap.risky,
            R.mipmap.other,
    };

    private List<HomeItem> mData;
    private BaseQuickAdapter mAdapter;

    public List<?> images;
    public List<String> titles;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.ll_title)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_source;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(TITLES[i]);
            item.setResId(IMAGES[i]);
            mData.add(item);
        }
        String[] urls = getResources().getStringArray(R.array.source_banner_url);
        String[] tips = getResources().getStringArray(R.array.source_banner_title);
        images = new ArrayList(Arrays.asList(urls));
        titles = new ArrayList(Arrays.asList(tips));
    }

    @Override
    protected void initView() {
        tv_title.setText("影库资源");
        initRecyclerView();

        //默认是CIRCLE_INDICATOR
        banner.setImages(images)
                .setBannerTitles(titles)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    private void initRecyclerView() {
        rv_list.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new HomeAdapter(R.layout.item_home, mData);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SourceActivity.this, MovieListActivity.class);
                intent.putExtra("title", mData.get(position).getTitle());
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
        }
    }
}
