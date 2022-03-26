package com.example.myshopping.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myshopping.R;
import com.example.myshopping.service.RemindIntentService;

public class MessageFragment extends Fragment {
    private EditText et_goods;
    private View view;
    private AppCompatActivity activity;
    private final static String STANDARD_ACTION = "com.example.myshopping.standard";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_broad_send, container, false);
        Toolbar tl_head = view.findViewById(R.id.tl_head);
        tl_head.setTitle("消息"); // 设置工具栏的标题文字
        tl_head.setTitleTextColor(getResources().getColor(R.color.orange));
        et_goods = view.findViewById(R.id.et_goods);
        activity = (AppCompatActivity) getActivity();
        // 注册接收器，注册之后才能正常接收
        view.findViewById(R.id.btn_find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_goods.getText())) {
                    Toast.makeText(v.getContext(), "请填写商品名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(STANDARD_ACTION); // 创建指定动作的意图
                intent.putExtra("goods_name", et_goods.getText().toString());
                activity.sendBroadcast(intent); // 发送标准广播
            }

        });
        return view;

    }

    private SendReceiver sendReceiver;

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sendReceiver = new SendReceiver(); // 创建一个标准广播的接收器
        // 创建一个意图过滤器，只处理STANDARD_ACTION的广播
        IntentFilter filter = new IntentFilter(STANDARD_ACTION);
        context.registerReceiver(sendReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(sendReceiver);
    }

    private class SendReceiver extends BroadcastReceiver {
        // 一旦接收到标准广播，马上触发接收器的onReceive方法
        @Override
        public void onReceive(Context context, Intent intent) {
            // 广播意图非空，且接头暗号正确
            if (intent != null && intent.getAction().equals(STANDARD_ACTION)) {
                intent = new Intent(context, RemindIntentService.class);
                intent.putExtra("goods_name", intent.getStringExtra("goods_name"));
                context.startService(intent);
            }
        }
    }
}
