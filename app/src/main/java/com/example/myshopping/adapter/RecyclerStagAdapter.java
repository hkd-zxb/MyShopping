package com.example.myshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopping.R;
import com.example.myshopping.bean.NewsInfo;

import java.util.List;

public class RecyclerStagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NewsInfo> newsInfos;

    public RecyclerStagAdapter(Context mContext, List<NewsInfo> newsInfos) {
        this.mContext = mContext;
        this.newsInfos = newsInfos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_staggered, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemHolder holder1 = (ItemHolder) holder;
        holder1.tv_title.setText(newsInfos.get(position).title);
        holder1.iv_pic.setImageResource(newsInfos.get(position).pic_id);
    }

    @Override
    public int getItemCount() {
        return newsInfos.size();
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_item;
        private ImageView iv_pic;
        private TextView tv_title;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            iv_pic = itemView.findViewById(R.id.iv_pic);
            tv_title = itemView.findViewById(R.id.tv_title);

        }
    }
}