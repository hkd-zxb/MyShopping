package com.example.myshopping;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myshopping.bean.CartInfo;
import com.example.myshopping.bean.GoodsInfo;
import com.example.myshopping.database.CartsDBHelper;
import com.example.myshopping.database.GoodsDBHelper;
import com.example.myshopping.fragment.GoodsDetailFragment;

public class GoodsDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "GoodsDetailsActivity";
    public Button btn_add_cart;
    int flag = 1;
    private Button btn_like;
//    private ImageView iv_back;
    private GoodsDBHelper mHelper;
    private CartsDBHelper cartsDBHelper;
    private GoodsInfo info;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        btn_add_cart = findViewById(R.id.btn_add_cart);
        btn_like = findViewById(R.id.btn_like);
//        iv_back = findViewById(R.id.iv_back);
        btn_add_cart.setOnClickListener(this);
        btn_like.setOnClickListener(this);
//        iv_back.setOnClickListener(this);
//        Toolbar tl_head=findViewById(R.id.tl_head);
//        tl_head.setTitle("商品详情");
//        tl_head.setTitleTextColor(getResources().getColor(R.color.orange));
//        this.setSupportActionBar(tl_head);
        getSupportFragmentManager().beginTransaction().add(R.id.ll_item, new GoodsDetailFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHelper = GoodsDBHelper.getInstance(this, 1);
        mHelper.openWriteLink();
        cartsDBHelper = CartsDBHelper.getInstance(this, 1);
        cartsDBHelper.openWriteLink();
        Bundle bundle = getIntent().getExtras();
        info = mHelper.queryByGood_id(bundle.getInt("good_id"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_cart:
                addCart();
                break;
            case R.id.btn_like:
                addLike();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void addCart() {
        CartInfo cartInfo = cartsDBHelper.queryByCart_id(info.good_id);
        if (cartInfo != null) {
            cartInfo.count++;
            cartsDBHelper.update(cartInfo);
            Log.d(TAG, "onClick: " + cartInfo.count);
        } else {
            cartInfo = new CartInfo();
            cartInfo.count = 1;
            cartInfo.desc = info.desc;
            cartInfo.pic = info.pic;
            cartInfo.name = info.name;
            cartInfo.cart_id = info.good_id;
            cartInfo.price = info.price;
            cartsDBHelper.insert(cartInfo);
        }
//                Log.d(TAG, "onClick: " + cartInfo.cart_id);
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
    }

    private void addLike() {
        switch (flag) {
            case 0:
                btn_like.setActivated(false);
                flag = 1;
                Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                btn_like.setActivated(true);
                flag = 0;
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}