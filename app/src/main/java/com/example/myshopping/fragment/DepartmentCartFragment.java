package com.example.myshopping.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopping.R;
import com.example.myshopping.adapter.RecycleCartAdapter;
import com.example.myshopping.bean.CartInfo;
import com.example.myshopping.database.CartsDBHelper;

import java.util.List;

public class DepartmentCartFragment extends Fragment {
    public static final String TAG = "DepartmentCartFragment";
    protected View mView; // 声明一个视图对象
    protected AppCompatActivity mActivity; // 声明一个活动对象
    private CartsDBHelper mHelper;
    private RecyclerView rv_cart;
    private TextView tv_SumCount;
    private TextView tv_SumMoney;
    private Button btn_clear;
    private Button btn_settlement;
    private CheckBox cb_all;
    private LinearLayout ll_tab;
    private boolean flag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (AppCompatActivity) getActivity();
        Log.d(TAG, "onCreateView: ");
        mView = inflater.inflate(R.layout.fragment_department_cart, container, false);
        Toolbar tl_head = mView.findViewById(R.id.tl_head);
        tl_head.setTitle("购物车"); // 设置工具栏的标题文字
        tl_head.setTitleTextColor(getResources().getColor(R.color.orange));
        rv_cart = mView.findViewById(R.id.rv_cart);
        tv_SumCount = mView.findViewById(R.id.tv_SumCount);
        tv_SumMoney = mView.findViewById(R.id.tv_SumMoney);
//        btn_clear = mView.findViewById(R.id.btn_clear);
//        btn_settlement = mView.findViewById(R.id.btn_settlement);
        cb_all = mView.findViewById(R.id.cb_all);
        cb_all.setChecked(false);
//        ll_tab = mView.findViewById(R.id.ll_tab);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        btn_clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mHelper.deleteAll();
//                onResume();
//            }
//        });
        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tv_SumCount.setText("共" + mHelper.querySum("count") + "件商品");
                    tv_SumMoney.setText("￥" + mHelper.querySum("price*count"));
                } else {
                    tv_SumCount.setText("");
                    tv_SumMoney.setText("￥0");
                }
                flag = isChecked;
                onResume();
                Log.d(TAG, "onCheckedChanged: " + flag);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        mHelper = CartsDBHelper.getInstance(mActivity, 1);
        mHelper.openWriteLink();
        initView();
    }

    @Override
    public void onPause() {
        super.onPause();
        mHelper.closeLink();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    public void initView() {
        List<CartInfo> infoList = mHelper.query("1=1");
        GridLayoutManager manager = new GridLayoutManager(mActivity, 1);
        RecycleCartAdapter adapter = new RecycleCartAdapter(mActivity, infoList, flag);
        rv_cart.setAdapter(adapter);
        rv_cart.setLayoutManager(manager);
        if (infoList == null || infoList.size() <= 0) {
            Toast.makeText(mActivity, "你还没有商品加入购物车", Toast.LENGTH_SHORT).show();
        } else {
//            ll_tab.setVisibility(View.VISIBLE);
        }


    }
}
