package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kymjs.rxvolley.toolbox.Loger;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.adapter.AudioListAdapter;
import com.lzx.listenmovieapp.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 大众配音
 *
 * @author cx
 */
public class DubbingActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    List<File> mData = new ArrayList<>();
    BaseQuickAdapter mAdapter;
    File file;

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
        File file = new File("assets/成都.lrc");
        mData.add(file);
//        try {
//            writeBytesToFile(DubbingActivity.this.getAssets().open("成都.lrc"), file);
//            mData.add(file);
//        } catch (Exception e) {
//            Log.e("error", e.getMessage());
//        }
    }

    @Override
    protected void initView() {
        tv_title.setText("大众配音");
        initRecyclerView();
    }

    private void initRecyclerView() {
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AudioListAdapter(R.layout.item_audio_list, mData);
        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            Intent intent = new Intent(DubbingActivity.this, RecorderDubbingActivity.class);
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

    public void writeBytesToFile(InputStream is, File file) throws IOException {
        FileOutputStream fos = null;
        try {
            byte[] data = new byte[2048];
            int nbread = 0;
            fos = new FileOutputStream(file);
            while ((nbread = is.read(data)) > -1) {
                fos.write(data, 0, nbread);
            }
        } catch (Exception ex) {
            Log.e("TAG", ex.getMessage());
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}
