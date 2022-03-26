package com.example.myshopping.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.myshopping.bean.GoodsInfo;
import com.example.myshopping.bean.GoodsInfo;
import com.example.myshopping.bean.GoodsInfo;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("DefaultLocale")
public class GoodsDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "UserDBHelper";
    private static final String DB_NAME = "goods.db"; // 数据库的名称
    private static final int DB_VERSION =1; // 数据库的版本号
    private static GoodsDBHelper mHelper = null; // 数据库帮助器的实例
    private SQLiteDatabase mDB = null; // 数据库的实例
    public static final String TABLE_NAME = "goods_info"; // 表的名称

    private GoodsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private GoodsDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static GoodsDBHelper getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new GoodsDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new GoodsDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mDB != null && mDB.isOpen()) {
            mDB.close();
            mDB = null;
        }
    }

    // 创建数据库，执行建表语句
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        Log.d(TAG, "drop_sql:" + drop_sql);
        db.execSQL(drop_sql);
        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "good_id INTEGER PRIMARY KEY NOT NULL,"
                + "name VARCHAR," + "pic INTEGER,"
                 + "price FLOAT," +
                //演示数据库升级时要先把下面这行注释
                 "desc VARCHAR"+
                 ");";
        Log.d(TAG, "create_sql:" + create_sql);
        db.execSQL(create_sql); // 执行完整的SQL语句
    }

    // 修改数据库，执行表结构变更语句
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade oldVersion=" + oldVersion + ", newVersion=" + newVersion);
        if (newVersion > 1) {
            //Android的ALTER命令不支持一次添加多列，只能分多次添加
            String alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "phone VARCHAR;";
            Log.d(TAG, "alter_sql:" + alter_sql);
            db.execSQL(alter_sql);
            alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "password VARCHAR;";
            Log.d(TAG, "alter_sql:" + alter_sql);
            db.execSQL(alter_sql);
        }
    }

    // 根据指定条件删除表记录
    public int delete(String condition) {
        // 执行删除记录动作，该语句返回删除记录的数目
        return mDB.delete(TABLE_NAME, condition, null);
    }

    // 删除该表的所有记录
    public int deleteAll() {
        // 执行删除记录动作，该语句返回删除记录的数目
        return mDB.delete(TABLE_NAME, "1=1", null);
    }

    // 往该表添加一条记录
    public long insert(GoodsInfo info) {
        List<GoodsInfo> infoList = new ArrayList<GoodsInfo>();
        infoList.add(info);
        return insert(infoList);
    }

    // 往该表添加多条记录
    public long insert(List<GoodsInfo> infoList) {
        for (int i = 0; i < infoList.size(); i++) {
            GoodsInfo info = infoList.get(i);
            List<GoodsInfo> tempList = new ArrayList<GoodsInfo>();
            // 如果存在同名记录，则更新记录
            // 注意条件语句的等号后面要用单引号括起来
            String condition = String.format("good_id='%s'", info.good_id);
            tempList = query(condition);
            if (tempList.size() > 0) {
                update(info, condition);
//                    result = tempList.get(0).rowid;
                continue;
            }else {
                ContentValues cv = new ContentValues();
                cv.put("name", info.name);
                cv.put("pic", info.pic);
                cv.put("desc", info.desc);
                cv.put("price", info.price);
                cv.put("good_id", info.good_id);
                // 执行插入记录动作，该语句返回插入记录的行号
                mDB.insert(TABLE_NAME, "", cv);
            }
        }
        return 0;
    }

    // 根据条件更新指定的表记录
    public int update(GoodsInfo info, String condition) {
        ContentValues cv = new ContentValues();
        cv.put("name", info.name);
        cv.put("pic", info.pic);
        cv.put("desc", info.desc);
        cv.put("price", info.price);
        // 执行更新记录动作，该语句返回更新的记录数量
        return mDB.update(TABLE_NAME, cv, condition, null);
    }

    public int update(GoodsInfo info) {
        // 执行更新记录动作，该语句返回更新的记录数量
//        return update(info, "rowid=" + info.rowid);
        return 0;
    }

    // 根据指定条件查询记录，并返回结果数据列表
    public List<GoodsInfo> query(String condition) {
        String sql = String.format("select * from %s where %s;", TABLE_NAME, condition);
        Log.d(TAG, "query sql: " + sql);
        List<GoodsInfo> infoList = new ArrayList<GoodsInfo>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mDB.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            GoodsInfo info = new GoodsInfo();
            info.good_id=cursor.getInt(0);
            info.name=cursor.getString(1);
            info.pic = cursor.getInt(2);//取出字符串
            info.price=cursor.getInt(3);
            info.desc=cursor.getString(4);
            infoList.add(info);
        }
        cursor.close(); // 查询完毕，关闭数据库游标
        return infoList;
    }

    // 根据手机号码查询指定记录
    public GoodsInfo queryByGood_id(int good_id) {
        GoodsInfo info = null;
        List<GoodsInfo> infoList = query(String.format("good_id='%s'", good_id));
        if (infoList.size() > 0) {
            info = infoList.get(0);
        }
        return info;
    }

}
