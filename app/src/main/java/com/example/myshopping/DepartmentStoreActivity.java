package com.example.myshopping;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myshopping.adapter.DepartmentPagerAdapter;
import com.example.myshopping.bean.GoodsInfo;
import com.example.myshopping.database.GoodsDBHelper;
import com.example.myshopping.util.FileUtil;
import com.example.myshopping.util.SharedUtil;

import java.util.ArrayList;


public class DepartmentStoreActivity extends AppCompatActivity {
    public static final String TAG = "DepartmentStoreActivity";
    private ViewPager2 vp_content; // 声明一个翻页视图对象
    private RadioGroup rg_tabbar; // 声明一个单选组对象
    private GoodsDBHelper mHelper;
    private String mFirst = "true"; // 是否首次打开

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_store);
        vp_content = findViewById(R.id.vp_content);
        // 构建一个翻页适配器
        DepartmentPagerAdapter adapter = new DepartmentPagerAdapter(this);
        vp_content.setAdapter(adapter); // 设置翻页视图的适配器
        // 给翻页视图添加页面变更监听器
        vp_content.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                rg_tabbar.check(rg_tabbar.getChildAt(position).getId());
            }
        });
        rg_tabbar = findViewById(R.id.rg_tabbar);
        // 设置单选组的选中监听器
        rg_tabbar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int pos = 0; pos < rg_tabbar.getChildCount(); pos++) {
                    // 获得指定位置的单选按钮
                    RadioButton tab = (RadioButton) rg_tabbar.getChildAt(pos);
                    if (tab.getId() == checkedId) { // 正是当前选中的按钮
                        vp_content.setCurrentItem(pos); // 设置翻页视图显示第几页
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mHelper = GoodsDBHelper.getInstance(this, 1);
        mHelper.openWriteLink(); // 打开商品数据库的写连接
        downloadGoods();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        mHelper.closeLink();
    }

    public void downloadGoods() {
        // 获取共享参数保存的是否首次打开参数
        mFirst = SharedUtil.getIntance(this).readString("first", "true");
        // 获取当前App的私有下载路径
        String path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/";
        if (mFirst.equals("true")) { // 如果是首次打开
            ArrayList<GoodsInfo> goodsList = GoodsInfo.getDefaultList(); // 模拟网络图片下载
            for (int i = 0; i < goodsList.size(); i++) {
                GoodsInfo info = goodsList.get(i);
                info.good_id = i;
                long rowid = mHelper.insert(info); // 往商品数据库插入一条该商品的记录
                info.rowid = rowid;
                Bitmap pic = BitmapFactory.decodeResource(getResources(), info.pic);
                String pic_path = path + rowid + ".jpg";
                FileUtil.saveImage(pic_path, pic); // 往存储卡保存商品图片
                pic.recycle(); // 回收位图对象
                info.pic_path = pic_path;
                mHelper.update(info); // 更新商品数据库中该商品记录的图片路径
            }
        }
        // 把是否首次打开写入共享参数
        SharedUtil.getIntance(this).writeString("first", "false");
    }
}
