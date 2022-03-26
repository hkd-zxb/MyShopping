package com.example.myshopping.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myshopping.bean.CartInfo;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("DefaultLocale")
public class CartsDBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "cart_info"; // 表的名称
    private static final String TAG = "UserDBHelper";
    private static final String DB_NAME = "cart.db"; // 数据库的名称
    private static final int DB_VERSION = 1; // 数据库的版本号
    private static CartsDBHelper mHelper = null; // 数据库帮助器的实例
    private SQLiteDatabase mDB = null; // 数据库的实例

    private CartsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private CartsDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static CartsDBHelper getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new CartsDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new CartsDBHelper(context);
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
                + "cart_id INTEGER PRIMARY KEY ,"
                + "name VARCHAR," + "pic INTEGER,"
                + "price FLOAT," +
                //演示数据库升级时要先把下面这行注释
                "desc VARCHAR," + "count INTEGER" +
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
    public long insert(CartInfo info) {
        List<CartInfo> infoList = new ArrayList<CartInfo>();
        infoList.add(info);
        return insert(infoList);
    }

    // 往该表添加多条记录
    public long insert(List<CartInfo> infoList) {
        for (int i = 0; i < infoList.size(); i++) {
            CartInfo info = infoList.get(i);
            List<CartInfo> tempList = new ArrayList<CartInfo>();
            // 如果存在同名记录，则更新记录
            // 注意条件语句的等号后面要用单引号括起来
            String condition = String.format("cart_id='%s'", info.cart_id);
            tempList = query(condition);
            if (tempList.size() > 0) {
                info.count++;
                update(info, condition);
//                    result = tempList.get(0).rowid;
//                    continue;
            } else {
                ContentValues cv = new ContentValues();
                cv.put("name", info.name);
                cv.put("pic", info.pic);
                cv.put("desc", info.desc);
                cv.put("price", info.price);
                cv.put("count", info.count);
                cv.put("cart_id", info.cart_id);
                // 执行插入记录动作，该语句返回插入记录的行号
                mDB.insert(TABLE_NAME, "", cv);
            }
        }
        return 0;
    }

    // 根据条件更新指定的表记录
    public int update(CartInfo info, String condition) {
        ContentValues cv = new ContentValues();
//        cv.put("name", info.name);
//        cv.put("pic", info.pic);
//        cv.put("desc", info.desc);
//        cv.put("price", info.price);
        cv.put("count", info.count);
//        cv.put("cart_id", info.cart_id);
        // 执行更新记录动作，该语句返回更新的记录数量
        return mDB.update(TABLE_NAME, cv, condition, null);
    }

    public int update(CartInfo info) {
        // 执行更新记录动作，该语句返回更新的记录数量
        return update(info, "cart_id=" + info.cart_id);

    }

    // 根据指定条件查询记录，并返回结果数据列表
    public List<CartInfo> query(String condition) {
        String sql = String.format("select * from %s where %s;", TABLE_NAME, condition);
        Log.d(TAG, "query sql: " + sql);
        List<CartInfo> infoList = new ArrayList<CartInfo>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mDB.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            CartInfo info = new CartInfo();
            info.cart_id = cursor.getInt(0);
            info.name = cursor.getString(1);
            info.pic = cursor.getInt(2);//取出字符串
            info.price = cursor.getInt(3);
            info.desc = cursor.getString(4);
            info.count = cursor.getInt(5);
            infoList.add(info);
        }
        cursor.close(); // 查询完毕，关闭数据库游标
        return infoList;
    }

    public int querySum(String condition) {
        String sql = String.format("select sum(%s) from %s ;", condition,TABLE_NAME );
        Log.d(TAG, "query sql: " + sql);
        // 执行记录查询动作，该语句返回结果集的游标
        int sum=0;
        Cursor cursor = mDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        return sum;
        // 根据手机号码查询指定记录
    }
    public int querySumByCart_id(String condition1,String condition2) {
        String sql = String.format("select sum(%s) from %s where cart_id=''%s;", condition1,TABLE_NAME,condition2 );
        Log.d(TAG, "query sql: " + sql);
        // 执行记录查询动作，该语句返回结果集的游标
        int sum=0;
        Cursor cursor = mDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        return sum;
        // 根据手机号码查询指定记录
    }
    public CartInfo queryByCart_id(int cart_id) {
        CartInfo info = null;
        List<CartInfo> infoList = query(String.format("cart_id='%s'", cart_id));
        if (infoList.size() > 0) {
            info = infoList.get(0);
        }
        return info;
    }

}
