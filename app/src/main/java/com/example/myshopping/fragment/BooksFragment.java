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

public class BooksFragment extends Fragment {
    private Context mContext;
    private View mView;
    private SwipeRefreshLayout srl_clothes; // 声明一个下拉刷新布局对象
    private RecyclerView rv_clothes; // 声明一个循环视图对象
    private List<NewsInfo> mAllList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_books, container, false);
        return mView;
    }
}
