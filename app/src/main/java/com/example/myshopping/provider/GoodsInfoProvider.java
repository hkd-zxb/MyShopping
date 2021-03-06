package com.example.myshopping.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.myshopping.database.GoodsDBHelper;

public class GoodsInfoProvider extends ContentProvider {
    private final static String TAG = "UserInfoProvider";
    private GoodsDBHelper userDB; // 声明一个用户数据库的帮助器对象
    public static final int USER_INFO = 1; // Uri匹配时的代号
    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static { // 往Uri匹配器中添加指定的数据路径
        uriMatcher.addURI(GoodsInfoContent.AUTHORITIES, "/goods", USER_INFO);
    }

    // 创建ContentProvider时调用，可在此获取具体的数据库帮助器实例
    @Override
    public boolean onCreate() {
        userDB = GoodsDBHelper.getInstance(getContext(), 1);
        return true;
    }

    // 插入数据
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) == USER_INFO) { // 匹配到了用户信息表
            // 获取SQLite数据库的写连接
            SQLiteDatabase db = userDB.getWritableDatabase();
            // 向指定的表插入数据，返回记录的行号
            long rowId = db.insert(GoodsInfoContent.TABLE_NAME, null, values);
            if (rowId > 0) { // 判断插入是否执行成功
                // 如果添加成功，就利用新记录的行号生成新的地址
                Uri newUri = ContentUris.withAppendedId(GoodsInfoContent.CONTENT_URI, rowId);
                // 通知监听器，数据已经改变
                getContext().getContentResolver().notifyChange(newUri, null);
            }
            db.close(); // 关闭SQLite数据库连接
        }
        return uri;
    }

    public void close() {
        userDB.closeLink();
    }    // 根据指定条件删除数据

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        if (uriMatcher.match(uri) == USER_INFO) { // 匹配到了用户信息表
            // 获取SQLite数据库的写连接
            SQLiteDatabase db = userDB.getWritableDatabase();
            // 执行SQLite的删除操作，并返回删除记录的数目
            count = db.delete(GoodsInfoContent.TABLE_NAME, selection, selectionArgs);
            db.close(); // 关闭SQLite数据库连接
        }
        return count;
    }

    // 根据指定条件查询数据库
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        if (uriMatcher.match(uri) == USER_INFO) { // 匹配到了用户信息表
            // 获取SQLite数据库的读连接
            SQLiteDatabase db = userDB.getReadableDatabase();
            // 执行SQLite的查询操作
            cursor = db.query(GoodsInfoContent.TABLE_NAME,
                    projection, selection, selectionArgs, null, null, sortOrder);
            // 设置内容解析器的监听
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            userDB.closeLink();
        }

        return cursor; // 返回查询结果集的游标
    }

    // 获取Uri支持的数据类型，暂未实现
    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // 更新数据，暂未实现
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
