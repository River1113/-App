package com.lzx.listenmovieapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.bean.HomeItem;
import com.lzx.listenmovieapp.bean.MovieListInfo;
import com.lzx.listenmovieapp.http.Config;

import java.util.List;

/**
 * Author：Road
 * Date  ：2018/4/26
 */

public class MovieListAdapter extends BaseQuickAdapter<MovieListInfo, BaseViewHolder> {

    public MovieListAdapter(int layoutResId, @Nullable List<MovieListInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieListInfo item) {
        ImageView imageView = helper.getView(R.id.iv_item);
        Glide.with(mContext).load(Config.RESOURCE_URL + item.getImg())
                .centerCrop()
                .placeholder(R.mipmap.image_placeholder)
                .into(imageView);
        helper.setText(R.id.tv_name_item, item.getName());
        helper.setText(R.id.tv_desc_item, item.getDesc());
    }
}
