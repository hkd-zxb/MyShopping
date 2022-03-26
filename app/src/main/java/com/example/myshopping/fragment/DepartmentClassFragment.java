package com.example.myshopping.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myshopping.R;
import com.example.myshopping.adapter.ClassPagerAdapter;
import com.example.myshopping.adapter.SearchAdapter;
import com.example.myshopping.bean.Bean;
import com.example.myshopping.bean.GoodsInfo;
import com.example.myshopping.database.GoodsDBHelper;
import com.example.myshopping.widget.SearchView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class DepartmentClassFragment extends Fragment implements SearchView.SearchViewListener {
    public static final String TAG = "DepartmentClassFragment";
    private GoodsDBHelper mHelper;
    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;
    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;
    protected View view;
    private AppCompatActivity activity;
    private List<String> mtitleList = new ArrayList<>();
    private ListView lvResults;
    /**
     * 搜索view
     */
    private SearchView searchView;
    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;
    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;
    /**
     * 搜索结果列表adapter
     */
    private SearchAdapter resultAdapter;
    private List<GoodsInfo> goodsInfoList;
    /**
     * 热搜版数据
     */
    private List<String> hintData;
    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;
    /**
     * 搜索结果的数据
     */
    private List<GoodsInfo> resultData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (AppCompatActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_department_class, container, false);
        lvResults = view.findViewById(R.id.main_lv_search_results);
        searchView = view.findViewById(R.id.main_search_layout);
        mtitleList.add("服装");
        mtitleList.add("图书");
        mtitleList.add("美食");
        mtitleList.add("数码");
        TabLayout tabLayout = view.findViewById(R.id.tab_title);
        ViewPager2 vp2_content = view.findViewById(R.id.vp2_content);
        ClassPagerAdapter adapter = new ClassPagerAdapter(activity, mtitleList);
        vp2_content.setAdapter(adapter);
        vp2_content.setCurrentItem(0);
        Log.d(TAG, "onCreateView: " + vp2_content.getCurrentItem());
        new TabLayoutMediator(tabLayout, vp2_content, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                tab.setText(mtitleList.get(position)); // 设置每页的标签文字
            }
        }).attach();
//        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHelper = GoodsDBHelper.getInstance(activity, 1);
        mHelper.openWriteLink();
        goodsInfoList = mHelper.query("1=1");
        initData();
        initViews();
    }

    @Override
    public void onPause() {
        super.onPause();
        mHelper.closeLink();
    }

    private void initViews() {
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        //从数据库获取数据
//        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 获取db 数据
     */
    private void getDbData() {
//        int size = 100;
//
//        for (int i = 0; i < size; i++) {
//            dbData.add(new Bean(R.drawable.icon, "android开发必备技能" + (i + 1), "Android自定义view——自定义搜索view", i * 20 + 2 + ""));
//        }
//        Log.d(TAG, "getDbData: " + dbData.size());
    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);
        for (int i = 1; i <= hintSize; i++) {
            hintData.add("热搜版" + i + "：手机");
        }
        hintAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, hintData);
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < goodsInfoList.size()
                    && count < hintSize; i++) {
                if (goodsInfoList.get(i).name.contains(text.trim())) {
                    autoCompleteData.add(goodsInfoList.get(i).name);
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < goodsInfoList.size(); i++) {
                if (goodsInfoList.get(i).name.contains(text.trim())) {
                    resultData.add(goodsInfoList.get(i));
                }
            }
        }
        if (resultAdapter == null) {
            resultAdapter = new SearchAdapter(getContext(), resultData, R.layout.item_bean_list);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     *
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (lvResults.getAdapter() == null) {
            //获取搜索数据 设置适配器
            lvResults.setAdapter(resultAdapter);
        } else {
            //更新搜索数据
            resultAdapter.notifyDataSetChanged();
        }
        Toast.makeText(getContext(), "完成搜索", Toast.LENGTH_SHORT).show();


    }

}
