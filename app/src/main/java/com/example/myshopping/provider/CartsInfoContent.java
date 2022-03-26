package com.example.myshopping.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.myshopping.database.GoodsDBHelper;


public class CartsInfoContent implements BaseColumns {
    // 这里的名称必须与AndroidManifest.xml里的android:authorities保持一致
    public static final String AUTHORITIES = "com.example.myshopping.provider.GoodsInfoProvider";
    //  内容提供器的外部表名
    public static final String TABLE_NAME = GoodsDBHelper.TABLE_NAME;
    // 访问内容提供器的URI
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/goods");
    // 下面是该表的各个字段名称
    public static final String GOODS_NAME = "name";
    public static final String GOODS_DESC = "desc";
    public static final String GOODS_PRICE = "price";
    public static final String PIC ="pic" ;
    // 默认的排序方法
    public static final String DEFAULT_SORT_ORDER = "_id desc";
}
