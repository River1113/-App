package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.adapter.AudioListAdapter;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.download.StorageConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 我的配音
 *
 * @author cx
 */
public class MyDubbingActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    List<File> mData = new ArrayList<>();
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
        return R.layout.activity_dubbing;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        File file = StorageConfig.getAudioPath();
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        mData.addAll(Arrays.asList(files));
    }

    @Override
    protected void initView() {
        tv_title.setText("我的配音");
        initRecyclerView();
    }

    private void initRecyclerView() {
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AudioListAdapter(R.layout.item_audio_list, mData);
        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            Intent intent = new Intent(MyDubbingActivity.this, PlayDubbingActivity.class);
            File file = mData.get(position);
            intent.putExtra("audio", file);
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
