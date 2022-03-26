package com.example.myshopping.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;


import com.example.myshopping.R;


public class RemindIntentService extends IntentService {
    private String mChannelId = "4"; // 通知渠道的编号
    private String mChannelName = "非常重要"; // 通知渠道的名称
    private int mImportance = NotificationManager.IMPORTANCE_HIGH; // 通知渠道的级别
    private Handler mHandler = new Handler(Looper.myLooper()); // 声明一个处理器对象

    public RemindIntentService() {
        super("RemindIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String goods_name = intent.getStringExtra("goods_name");
            mHandler.postDelayed(new RemindRunnable(goods_name), 1000);
        }
    }

    private class RemindRunnable implements Runnable {
        private String goods_name;

        public RemindRunnable(String goods_name) {
            this.goods_name = goods_name;
        }

        @Override
        public void run() {
            sendNotifyRemind(goods_name);
        }
    };

    // 发送指定渠道的通知消息（包括消息标题和消息内容）
    private void sendNotifyRemind(String goods_name) {
        String title = "物流信息提醒";
        String message = String.format("您购买的“%s”已经送达，请及时取件", goods_name);
        Notification.Builder builder = new Notification.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android 8.0开始必须给每个通知分配对应的渠道
            builder = new Notification.Builder(this, mChannelId);
        }
        builder.setContentIntent(null) // 设置内容的点击意图
                .setAutoCancel(true) // 点击通知栏后是否自动清除该通知
                .setSmallIcon(R.mipmap.ic_launcher) // 设置应用名称左边的小图标
                .setContentTitle(title) // 设置通知栏里面的标题文本
                .setContentText(message); // 设置通知栏里面的内容文本
        Notification notify = builder.build(); // 根据通知建造器构建一个通知对象
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotifyUtil.createNotifyChannel(this, mChannelId, mChannelName, mImportance);
//        }
//        NotifyUtil.showMarkerCount(this, 1, notify); // 在桌面的应用图标右上方显示指定数字的消息角标
//         从系统服务中获取通知管理器
        NotificationManager notifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 使用通知管理器推送通知，然后在手机的通知栏就会看到该消息，多条通知需要指定不同的通知编号
        notifyMgr.notify(Integer.parseInt(mChannelId), notify);
    }

}
