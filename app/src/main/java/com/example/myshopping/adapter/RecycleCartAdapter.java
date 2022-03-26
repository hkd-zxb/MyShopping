package com.example.myshopping.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopping.R;
import com.example.myshopping.bean.CartInfo;
import com.example.myshopping.database.CartsDBHelper;

import java.util.List;

public class RecycleCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "DepartmentCartFragment";
    //    private Context mContext; // 声明一个上下文对象
    protected AppCompatActivity mActivity;
    private List<CartInfo> cartInfoList;
    private CartsDBHelper cartsDBHelper;
    private boolean flag;
    private TextView tv_SumCount;
    private TextView tv_SumMoney;
    private ItemHolder holder2;
    private String condition = "";
    private CheckBox cb_all;

    public RecycleCartAdapter(AppCompatActivity mActivity, List<CartInfo> cartInfoList, boolean flag) {
        this.mActivity = mActivity;
        this.cartInfoList = cartInfoList;
        this.flag = flag;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.item_cart, parent, false);
        return new ItemHolder(v);
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemHolder holder1 = (ItemHolder) holder;
        holder2 = (ItemHolder) holder;
        cartsDBHelper = CartsDBHelper.getInstance(mActivity, 1);
        cartsDBHelper.openWriteLink();
        tv_SumCount = mActivity.findViewById(R.id.tv_SumCount);
        tv_SumMoney = mActivity.findViewById(R.id.tv_SumMoney);
        cb_all = mActivity.findViewById(R.id.cb_all);
//        CartInfo cartInfo = new CartInfo();
//        Cursor cursor = mContext.getContentResolver().query(CartsInfoContent.CONTENT_URI, null, null, null, null);
//        while (cursor.moveToNext()) {
//            cartInfo.pic = cursor.getInt(cursor.getColumnIndex(CartsInfoContent.PIC));
//            cartInfo.desc = cursor.getString(cursor.getColumnIndex(CartsInfoContent.GOODS_DESC));
//            cartInfoList.add(cartInfo);
//        }
//        cursor.close(); // 关闭数据库游标
//        for (CartInfo carts : cartInfoList) { // 遍历用户信息列表
//            holder1.tv_desc.setText(carts.desc);
//            holder1.iv_pic.setImageResource(carts.pic);
//        }

        holder1.tv_desc.setText(cartInfoList.get(position).desc);
        holder1.iv_pic.setImageResource(cartInfoList.get(position).pic);
        holder1.tv_name.setText(cartInfoList.get(position).name);
        holder1.tv_price.setText("￥" + (int) cartInfoList.get(position).price);
        holder1.tv_count.setText(cartInfoList.get(position).count + "");
        holder2.cb_cart.setChecked(flag);
        holder2.cb_cart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    condition += " or cart_id=" + cartInfoList.get(position).cart_id;
                    Log.d(TAG, "onCheckedChanged: " + cartsDBHelper.querySumByCart_id("count", condition));
                } else {
                    cb_all.setChecked(false);
                    condition = condition.replace("or cart_id=" + cartInfoList.get(position).cart_id, "");
                }
                tv_SumCount.setText("共" + cartsDBHelper.querySumByCart_id("count", condition) + "件商品");
                tv_SumMoney.setText("￥" + cartsDBHelper.querySumByCart_id("price*count", condition));
            }
        });
        holder1.btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartInfo cartInfo = cartsDBHelper.queryByCart_id(cartInfoList.get(position).cart_id);
                if (cartInfo != null) {
                    if (--cartInfo.count == 0) {
                        cartsDBHelper.delete("cart_id=" + cartInfo.cart_id);
                        holder1.fl_item.removeAllViews();
                    }
                    cartsDBHelper.update(cartInfo);
                    holder1.tv_count.setText(cartInfo.count + "");
                    tv_SumCount.setText("共" + cartsDBHelper.querySumByCart_id("count", condition) + "件商品");
                    tv_SumMoney.setText("￥" + cartsDBHelper.querySumByCart_id("price*count", condition));
                    Log.d(TAG, "onClick: " + cartInfo.count);
                }

            }
        });
        holder1.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartInfo cartInfo = cartsDBHelper.queryByCart_id(cartInfoList.get(position).cart_id);
                if (cartInfo != null) {
                    cartInfo.count++;
                    cartsDBHelper.update(cartInfo);
                    Log.d(TAG, "onClick: " + cartInfo.count);
                }
                holder1.tv_count.setText(cartInfo.count + "");
                tv_SumCount.setText("共" + cartsDBHelper.querySumByCart_id("count", condition) + "件商品");
                tv_SumMoney.setText("￥" + cartsDBHelper.querySumByCart_id("price*count", condition));
            }
        });
        Log.d(TAG, "onBindViewHolder: " + flag);
    }

    @Override
    public int getItemCount() {
        return cartInfoList.size();
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
        private FrameLayout fl_item;
        private ImageView iv_pic;
        private TextView tv_desc;
        private TextView tv_price;
        private TextView tv_name;
        private TextView tv_count;
        private CheckBox cb_cart;
        private Button btn_add;
        private Button btn_close;
        public TextView item_text;
        public TextView item_del;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            fl_item = itemView.findViewById(R.id.fl_item);
            iv_pic = itemView.findViewById(R.id.iv_pic);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_count = itemView.findViewById(R.id.tv_count);
            cb_cart = itemView.findViewById(R.id.cb_cart);
            btn_add = itemView.findViewById(R.id.btn_add);
            btn_close = itemView.findViewById(R.id.btn_close);
            item_text = itemView.findViewById(R.id.item_text);
            item_del = itemView.findViewById(R.id.item_del);

        }
    }
}
