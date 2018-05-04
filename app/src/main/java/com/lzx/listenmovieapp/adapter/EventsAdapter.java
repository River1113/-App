package com.lzx.listenmovieapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.bean.EventBean;

import java.util.List;

/**
 * Author：Road
 * Date  ：2018/4/26
 */

public class EventsAdapter extends BaseQuickAdapter<EventBean, BaseViewHolder> {

    public EventsAdapter(int layoutResId, @Nullable List<EventBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventBean item) {
        ImageView imageView = helper.getView(R.id.iv_coverImage_item);
        Glide.with(mContext).load(item.getCoverImage())
                .centerCrop()
                .placeholder(R.mipmap.image_placeholder)
                .into(imageView);
        helper.setText(R.id.tv_name_item, item.getEventName());
        //活动状态 (0.default,1.未开始,2.进行中，3.已结束)
        switch (item.getStatus()) {
            default:
                break;
            case 1:
                helper.setImageResource(R.id.iv_status_item,R.mipmap.ic_event_nostart);
                break;

            case 2:
                helper.setImageResource(R.id.iv_status_item,R.mipmap.ic_event_ongoing);
                break;

            case 3:
                helper.setImageResource(R.id.iv_status_item,R.mipmap.ic_event_end);
                break;
        }

    }
}
