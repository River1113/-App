package com.lzx.listenmovieapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.bean.MovieListInfo;
import com.lzx.listenmovieapp.http.Config;
import com.lzx.listenmovieapp.util.FileSizeUtil;

import java.io.File;
import java.util.List;

/**
 * Author：Road
 * Date  ：2018/4/26
 */

public class AudioListAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

    public AudioListAdapter(int layoutResId, @Nullable List<File> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, File item) {
        helper.setText(R.id.tv_name_item, item.getName());
        helper.setText(R.id.tv_size_item, FileSizeUtil.formetFileSize(item.length()));
    }
}
