package com.example.myshopping;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myshopping.bean.CartInfo;
import com.example.myshopping.bean.GoodsInfo;
import com.example.myshopping.database.CartsDBHelper;
import com.example.myshopping.database.GoodsDBHelper;

import java.util.List;

public class TestActivity extends AppCompatActivity {
    public static final String TAG="aaa";
    private ImageView iv_test;
    private TextView tv_desc;
    private CartsDBHelper mHelper;
private Button btn_on;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        iv_test = findViewById(R.id.iv_test);
        tv_desc = findViewById(R.id.tv_desc);
        btn_on=findViewById(R.id.btn_on);
        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper = CartsDBHelper.getInstance(v.getContext(), 1);
                mHelper.openReadLink();
//                CartInfo cartInfo=new CartInfo();
                CartInfo cartInfo=mHelper.queryByCart_id(0);
                cartInfo.count++;
                mHelper.update(cartInfo);
                Log.d(TAG, "onClick: " +cartInfo.count);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

//        mHelper.deleteAll();
//        List<CartInfo> info = mHelper.query("1=1");
//        if (info!=null&&info.size()>0){
//            for (int i = 0; i < info.size(); i++) {
//                iv_test.setImageResource(info.get(i).pic);
//                tv_desc.setText(info.get(i).desc);
//            }
//        }
//        iv_test.setImageURI(Uri.parse(info.pic_path));

//        Log.d(TAG, "onResume: "+info.pic_path);

    }
}