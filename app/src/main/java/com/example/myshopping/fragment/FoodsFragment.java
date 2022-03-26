package com.example.myshopping.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myshopping.R;
import com.example.myshopping.adapter.RecyclerStagAdapter;
import com.example.myshopping.bean.NewsInfo;

import java.util.List;

public class FoodsFragment extends Fragment {
    private Context mContext;
    private View mView;
    private SwipeRefreshLayout srl_clothes; // 声明一个下拉刷新布局对象
    private RecyclerView rv_clothes; // 声明一个循环视图对象
    private List<NewsInfo> mAllList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_foods, container, false);
//        srl_clothes = mView.findViewById(R.id.srl_clothes);
//        srl_clothes.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//        });
//        srl_clothes.setColorSchemeResources(
//                R.color.red, R.color.orange, R.color.green, R.color.blue);
//        rv_clothes = mView.findViewById(R.id.rv_clothes);
//        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
//        rv_clothes.setLayoutManager(manager); // 设置循环视图的布局管理器
//        mAllList = NewsInfo.getDefaultStag(); // 获取默认的服装信息列表
//        RecyclerStagAdapter adapter=new RecyclerStagAdapter(mContext,mAllList);
//        rv_clothes.setAdapter(adapter);
        return mView;
    }
}
