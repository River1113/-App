package com.lzx.listenmovieapp.ui;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.adapter.MovieListAdapter;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.MovieListInfo;
import com.lzx.listenmovieapp.utils.JsonUtil;
import com.lzx.listenmovieapp.utils.ToastUtil;

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
    //创建GSON工具类全局变量 SuperLi
    //private JsonUtil jsonUtil;
    String jsonString = "[{\n" +
       "\"name\" : \"摔跤吧!爸爸\",\n" +
       "\"img\"    : \"http://192.168.0.109:8080/image/摔跤吧!爸爸.jpg\",\n" +
       "\"desc\": \"马哈维亚曾经是一名前途无量的摔跤运动员，在放弃了职业生涯后，他最大的遗憾就是没有能够替国家赢得金牌。马哈维亚将这份希望寄托在了尚未出生的儿子身上，哪知道妻子接连给他生了两个女儿，取名吉塔和巴比塔。让马哈维亚没有想到的是，两个姑... [详情]\",\n" +
       "\"score\": \"http://192.168.0.109:8080/video/摔跤吧爸爸.mp3\"\n" +
       "},{\n" +
       "\"name\" : \"天下无贼\",\n" +
       "\"img\"    : \"http://192.168.0.109:8080/image/天下无贼.jpg\",\n" +
       "\"desc\": \"《天下无贼》是一部剧情电影，根据赵本夫的同名小说改编而成，由冯小刚执导，刘德华、王宝强、刘若英等人主演。影片讲述了民工傻根的纯朴、善良感化两个小偷王薄、王丽的故事\",\n" +
       "\"score\": \"http://192.168.0.109:8080/video/天下无贼.mp3\"\n" +
       "},{\n" +
       "\"name\" : \"北京遇上西雅图\",\n" +
       "\"img\"    : \"http://192.168.0.109:8080/image/北京遇上西雅图.jpg\",\n" +
       "\"desc\": \"焦娇（汤唯 饰）15岁就来到了澳门，过着颠沛流离的生活，之后成为了赌场公关。罗大牛（吴秀波 饰）早年来到美国，一番摸爬滚打，如今已经是小有名气的加州房...\",\n" +
       "\"score\": \"http://192.168.0.109:8080/video/北京遇上西雅图.mp3\"\n" +
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
        return R.layout.activity_movie_list;
    }

    @Override
    protected void initData() {
        List<MovieListInfo> list = JsonUtil.jsonToList(jsonString, MovieListInfo.class);
        Log.e("---","" + list.size());
        System.out.println("总条数"+list.size());
        title = getIntent().getStringExtra("title");

        mData = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MovieListInfo info = new MovieListInfo();
            //info.setImg("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1709857625,355767256&fm=179&w=115&h=161&img.JPEG");
            info.setImg(list.get(i).getImg());
            info.setName(list.get(i).getName());
            info.setDesc(list.get(i).getDesc());
            info.setScore(list.get(i).getScore());
            mData.add(info);
        }
    }

    @Override
    protected void initView() {
        tv_title.setText(title);
        initRecyclerView();
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void initRecyclerView() {
        rv_list.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MovieListAdapter(R.layout.item_movie_list, mData);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.MyToast(MovieListActivity.this, "您选择了" + mData.get(position).getName());
                //点击跳转到在线播放视频路径
                Intent intent = new Intent(MovieListActivity.this,OnlineMovieActivity.class);
                intent.putExtra("url",mData.get(position).getScore());
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
                        if (refreshLayout != null)
                            refreshLayout.setRefreshing(false);
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
