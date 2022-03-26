package com.example.myshopping.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myshopping.GoodsDetailsActivity;
import com.example.myshopping.R;
import com.example.myshopping.bean.GoodsInfo;
import com.example.myshopping.database.GoodsDBHelper;
import com.example.myshopping.util.ObservableScrollView;
import com.example.myshopping.util.Utils;
import com.example.myshopping.widget.BannerPager;

import java.util.ArrayList;
import java.util.List;

public class GoodsDetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "GoodsDetailFragment";
    public ImageView iv_pic; // 声明列表项图标的图像视图
    public TextView tv_desc;
    protected View view;
    private boolean flag = true;
    private GoodsInfo info;
    private AppCompatActivity activity;
    private GoodsDBHelper mHelper;
    private ObservableScrollView scrollView;
    private Toolbar toolbar;
    private ImageView iv_back;
    private ImageView iv_more;
    private LinearLayout lvHeader;
    //    private RoundImageView ivHeader;
    private View spite_line;
    private ImageView iv_shopping_cart;
    private BannerPager banner_pager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (AppCompatActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_goods_detail, container, false);
        mHelper = GoodsDBHelper.getInstance(activity, 1);
        mHelper.openWriteLink();
        Bundle bundle = getActivity().getIntent().getExtras();
        info = mHelper.queryByGood_id(bundle.getInt("good_id"));
        tv_desc = view.findViewById(R.id.tv_desc);
        scrollView = view.findViewById(R.id.scrollView);
        toolbar = view.findViewById(R.id.toolbar);
        iv_back = view.findViewById(R.id.iv_back);
        iv_more = view.findViewById(R.id.iv_more);
        lvHeader = view.findViewById(R.id.lv_header);
//        ivHeader = view.findViewById(R.id.iv_pic);
        iv_shopping_cart = view.findViewById(R.id.iv_shopping_cart);
        spite_line = view.findViewById(R.id.spite_line);
        banner_pager = view.findViewById(R.id.banner_pager);
        iv_back.setOnClickListener(this);
        iv_shopping_cart.setOnClickListener(this);
        iv_more.setOnClickListener(this);
//        ivHeader.setImageResource(info.pic);
        initView();
        initBanner();
//        tv_desc.setText(info.desc);
        Log.d(TAG, "onCreateView: " + info);
        return view;

    }

    private void initView() {

        //获取dimen属性中 标题和头部图片的高度
        @SuppressLint("ResourceType") final float title_height = getResources().getDimension(R.dimen.title_height);
        @SuppressLint("ResourceType") final float head_height = getResources().getDimension(R.dimen.head_height);

        //滑动事件回调监听（一次滑动的过程一般会连续触发多次）
        scrollView.setOnScrollListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScroll(int oldy, int dy, boolean isUp) {

                float move_distance = head_height - title_height;
                if (!isUp && dy <= move_distance) {//手指往上滑,距离未超过200dp
                    //标题栏逐渐从透明变成不透明
                    toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
                    TitleAlphaChange(dy, move_distance);//标题栏渐变
                    HeaderTranslate(dy);//图片视差平移

                } else if (!isUp && dy > move_distance) {//手指往上滑,距离超过200dp
                    TitleAlphaChange(1, 1);//设置不透明百分比为100%，防止因滑动速度过快，导致距离超过200dp,而标题栏透明度却还没变成完全不透的情况。

                    HeaderTranslate(head_height);//这里也设置平移，是因为不设置的话，如果滑动速度过快，会导致图片没有完全隐藏。

                    iv_back.setImageResource(R.mipmap.ic_back_dark);
                    iv_more.setImageResource(R.mipmap.ic_more_dark);
                    iv_shopping_cart.setImageResource(R.mipmap.ic_shopping_dark);
                    spite_line.setVisibility(View.VISIBLE);

                } else if (isUp && dy > move_distance) {//返回顶部，但距离头部位置大于200dp
                    //不做处理

                } else if (isUp && dy <= move_distance) {//返回顶部，但距离头部位置小于200dp
                    //标题栏逐渐从不透明变成透明
                    TitleAlphaChange(dy, move_distance);//标题栏渐变
                    HeaderTranslate(dy);//图片视差平移

                    iv_back.setImageResource(R.mipmap.ic_back);
                    iv_more.setImageResource(R.mipmap.ic_more);
                    iv_shopping_cart.setImageResource(R.mipmap.ic_shopping_cart);
                    spite_line.setVisibility(View.GONE);
                }
            }
        });
    }

    private void HeaderTranslate(float distance) {
        lvHeader.setTranslationY(-distance);
        banner_pager.setTranslationY(distance / 2);
    }

    private void TitleAlphaChange(int dy, float mHeaderHeight_px) {//设置标题栏透明度变化
        float percent = (float) Math.abs(dy) / Math.abs(mHeaderHeight_px);
        //如果是设置背景透明度，则传入的参数是int类型，取值范围0-255
        //如果是设置控件透明度，传入的参数是float类型，取值范围0.0-1.0
        //这里我们是设置背景透明度就好，因为设置控件透明度的话，返回ICON等也会变成透明的。
        //alpha 值越小越透明
        int alpha = (int) (percent * 255);
        toolbar.getBackground().setAlpha(alpha);//设置控件背景的透明度，传入int类型的参数（范围0~255）
        iv_back.getBackground().setAlpha(255 - alpha);
        iv_more.getBackground().setAlpha(255 - alpha);
        iv_shopping_cart.getBackground().setAlpha(255 - alpha);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private List<Integer> getImageList() {
        Log.d(TAG, "getImageList: ");
        ArrayList<Integer> imageList = new ArrayList<Integer>();
        imageList.add(info.pic);
        imageList.add(info.pic);
        return imageList;
    }

    private void initBanner() {
        // 从布局文件中获取名叫banner_pager的广告轮播条
        BannerPager banner = view.findViewById(R.id.banner_pager);
        // 获取广告轮播条的布局参数
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
        params.height = (int) (Utils.getScreenWidth(activity));
        banner.setLayoutParams(params); // 设置广告轮播条的布局参数
        banner.setImage(getImageList(), flag); // 设置广告轮播条的广告图片列表
        flag = false;
        Log.d(TAG, "initBanner: " + flag);
        banner.setOnBannerListener(this::onBannerClick); // 设置广告轮播条的广告点击监听器
        banner.start(); // 开始轮播广告图片
    }

    public void onBannerClick(int position) {
        String desc = String.format("您点击了第%d张图片", position + 1);
        Toast.makeText(activity, desc, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.iv_shopping_cart:
                Toast.makeText(getContext(), "点击了加入购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_more:
                Toast.makeText(getContext(), "点击了更多按键", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

