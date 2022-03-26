package com.example.myshopping.adapter;

import android.content.Context;

import com.example.myshopping.R;
import com.example.myshopping.bean.Bean;
import com.example.myshopping.bean.GoodsInfo;
import com.example.myshopping.util.CommonAdapter;
import com.example.myshopping.util.ViewHolder;

import java.util.List;

/**
 * Created by yetwish on 2015-05-11
 */

public class SearchAdapter extends CommonAdapter<GoodsInfo> {

    public SearchAdapter(Context context, List<GoodsInfo> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, int position) {
        holder.setImageResource(R.id.item_search_iv_icon, mData.get(position).good_id)
                .setText(R.id.item_search_tv_title, mData.get(position).name)
                .setText(R.id.item_search_tv_content, mData.get(position).desc)
                .setText(R.id.item_search_tv_comments, "￥"+(int) mData.get(position).price);
    }
}
