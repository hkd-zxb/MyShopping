package com.example.myshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopping.R;
import com.example.myshopping.bean.NewsInfo;
import com.example.myshopping.util.Utils;

import java.util.List;

public class RecyclerCombineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NewsInfo> mGoodsList;

    public RecyclerCombineAdapter(Context mContext, List<NewsInfo> mGoodsList) {
        this.mContext = mContext;
        this.mGoodsList = mGoodsList;
    }


    // 创建列表项的视图持有者
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_combine, parent, false);
        return new ItemHolder(v);
    }

    // 绑定列表项的视图持有者
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder holder1 = (ItemHolder) holder;
        holder1.tv_title.setText(mGoodsList.get(position).title);
        holder1.iv_pic.setImageResource(mGoodsList.get(position).pic_id);
        ViewGroup.LayoutParams iv_param = holder1.iv_pic.getLayoutParams();
        iv_param.height = Utils.dip2px(mContext, 90);
        holder1.iv_pic.setLayoutParams(iv_param);
    }


    // 获取列表项的个数
    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }

    // 定义列表项的视图持有者
    private class ItemHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_item;
        private ImageView iv_pic;
        private TextView tv_title;

        public ItemHolder(View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            iv_pic = itemView.findViewById(R.id.iv_pic);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
