package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.adapter.HomeAdapter;
import com.lzx.listenmovieapp.adapter.HomePagerAdapter;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.HomeItem;
import com.lzx.listenmovieapp.utils.ToastUtil;
import com.tmall.ultraviewpager.UltraViewPager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 主页面
 *
 * @author road
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    UltraViewPager viewPager;

    @BindView(R.id.ll_voice)
    LinearLayout ll_voice;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    private List<View> views;
    public static final int[] VIEWPAGERS = {R.mipmap.viewpaer, R.mipmap.viewpaer,
            R.mipmap.viewpaer, R.mipmap.viewpaer, R.mipmap.viewpaer
    };

    private BaseQuickAdapter mAdapter;
    private List<HomeItem> mData;

    public static final String[] TITLES = {"影库资源",
            "下载电影",
            "最近播放",
            "收藏",
            "远程点播",
            "大众配音",
            "活动专区",
            "设置",
            "我的",
    };

    public static final int[] IMAGES = {R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
    };

    private static final Class<?>[] ACTIVITYS = {SourceActivity.class,
            DownLoadActivity.class,
            RecentlyPlayedActivity.class,
            CollectActivity.class,
            RemotePlayedActivity.class,
            DubbingActivity.class,
            EventsActivity.class,
            SetActivity.class,
            MyActivity.class,
    };

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
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

        views = new ArrayList<>();
        // 将ImageView添加到view
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < VIEWPAGERS.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(params);
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            InputStream is = getResources().openRawResource(VIEWPAGERS[i]);
            Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
            BitmapDrawable bd = new BitmapDrawable(getResources(), bm);
            iv.setBackgroundDrawable(bd);
            views.add(iv);
        }
    }

    @Override
    protected void initView() {
        initRecyclerView();
        initViewPager();
    }

    @Override
    protected void setListener() {
        ll_voice.setOnClickListener(this);
    }

    private void initRecyclerView() {
        rv_list.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new HomeAdapter(R.layout.item_home, mData);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MainActivity.this, ACTIVITYS[position]);
                startActivity(intent);
            }
        });
        rv_list.setAdapter(mAdapter);
    }

    private void initViewPager() {
        viewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
        });
        viewPager.setAdapter(new HomePagerAdapter(views));
        viewPager.initIndicator();
        viewPager.getIndicator().setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.BLUE)
                .setNormalColor(Color.WHITE)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        viewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        viewPager.getIndicator().build();
        //设定页面循环播放
        viewPager.setInfiniteLoop(true);
        //设定页面自动切换  间隔2秒
        viewPager.setAutoScroll(2500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_voice:
                ToastUtil.show(this, "开始语音");
                break;
        }
    }

}
