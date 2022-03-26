package com.example.myshopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopping.GoodsDetailsActivity;
import com.example.myshopping.R;
import com.example.myshopping.bean.GoodsInfo;
import com.example.myshopping.database.GoodsDBHelper;
import com.example.myshopping.widget.RecyclerExtras;

import java.util.List;

public class MobileGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        RecyclerExtras.OnItemClickListener, RecyclerExtras.OnItemLongClickListener {
    private final static String TAG = "MobileGridAdapter";
    private Context mContext; // 声明一个上下文对象
    private List<GoodsInfo> mGoodsList;
    private GoodsDBHelper mHelper;
    // 声明列表项的点击监听器对象
    private RecyclerExtras.OnItemClickListener mOnItemClickListener;
    // 声明列表项的长按监听器对象
    private RecyclerExtras.OnItemLongClickListener mOnItemLongClickListener;

    public MobileGridAdapter(Context context, List<GoodsInfo> goodsList) {
        mContext = context;
        mGoodsList = goodsList;
    }

    // 获取列表项的个数
    public int getItemCount() {
        return mGoodsList.size();
    }

    // 创建列表项的视图持有者
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        // 根据布局文件item_grid.xml生成视图对象
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_grid, vg, false);
        return new ItemHolder(v);
    }

    // 绑定列表项的视图持有者
    public void onBindViewHolder(RecyclerView.ViewHolder vh, @SuppressLint("RecyclerView") final int position) {
        ItemHolder holder = (ItemHolder) vh;

//        if (mGoodsList!=null&&mGoodsList.size()>0){
//            for (int i = 0; i < mGoodsList.size(); i++) {
//
//            }
//        }
        holder.iv_pic.setImageResource(mGoodsList.get(position).pic);
        holder.tv_name.setText(mGoodsList.get(position).name);
        holder.tv_price.setText("￥"+(int) mGoodsList.get(position).price);
        holder.tv_desc.setText(mGoodsList.get(position).desc);
//        holder.tv_name.setText(mGoodsList.get(position).name);

        // 列表项的点击事件需要自己实现
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            }
        });
        // 列表项的长按事件需要自己实现
        holder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(v, position);
                }
                return true;
            }
        });
    }

    // 获取列表项的类型
    public int getItemViewType(int position) {
        return 0;
    }

    // 获取列表项的编号
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(RecyclerExtras.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(RecyclerExtras.OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    // 处理列表项的点击事件
    public void onItemClick(View view, int position) {
        int good_id = mGoodsList.get(position).good_id;
        Log.d(TAG, "onItemClick: " + good_id + "a" + position);
        Bundle bundle = new Bundle();
        bundle.putInt("good_id", good_id);
        Intent intent = new Intent(mContext, GoodsDetailsActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    // 处理列表项的长按事件
    public void onItemLongClick(View view, int position) {

    }

    // 定义列表项的视图持有者
    public class ItemHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_item; // 声明列表项的线性布局
        public ImageView iv_pic; // 声明列表项图标的图像视图
        public TextView tv_name; // 声明列表项标题的文本视图
        public TextView tv_price; // 声明列表项标题的文本视图
        public TextView tv_desc; // 声明列表项标题的文本视图

        public ItemHolder(View v) {
            super(v);
            ll_item = v.findViewById(R.id.ll_item);
            iv_pic = v.findViewById(R.id.iv_pic);
            tv_name = v.findViewById(R.id.tv_name);
            tv_price = v.findViewById(R.id.tv_price);
            tv_desc = v.findViewById(R.id.tv_desc);
        }
    }

}
