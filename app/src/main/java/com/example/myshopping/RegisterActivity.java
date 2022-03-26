package com.example.myshopping;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myshopping.bean.UserInfo;
import com.example.myshopping.database.UserDBHelper;

import java.util.Random;

@SuppressLint("DefaultLocale")
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_password; // 声明一个编辑框对象
    private EditText et_phone; // 声明一个编辑框对象
    private EditText et_verifycode; // 声明一个编辑框对象
    private String mVerifyCode; // 验证码
    private UserDBHelper mHelper;
    private String password;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // 从布局文件中获取名叫et_password_first的编辑框
        et_password = findViewById(R.id.et_password);
        // 从布局文件中获取名叫et_password_second的编辑框
        et_phone = findViewById(R.id.et_phone);
        // 从布局文件中获取名叫et_verifycode的编辑框
        et_verifycode = findViewById(R.id.et_verifycode);
        findViewById(R.id.btn_verifycode).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        password = et_password.getText().toString();
        phone = et_phone.getText().toString();
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.getId() == R.id.et_password && hasFocus) {
                    if (phone == null && phone.length() < 11) {
                        Toast.makeText(v.getContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // 从上一个页面获取要修改密码的手机号码
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHelper = UserDBHelper.getInstance(this, 4); // 获得用户数据库帮助器的实例
        mHelper.openWriteLink(); // 恢复页面，则打开数据库连接
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHelper.closeLink(); // 暂停页面，则关闭数据库连接
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_verifycode) { // 点击了“获取验证码”按钮
            if (phone == null || phone.length() < 11) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            // 生成六位随机数字的验证码
            mVerifyCode = String.format("%06d", new Random().nextInt(999999));
            // 以下弹出提醒对话框，提示用户记住六位验证码数字
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请记住验证码");
            builder.setMessage("手机号" + phone + "，本次验证码是" + mVerifyCode + "，请输入验证码");
            builder.setPositiveButton("好的", null);
            AlertDialog alert = builder.create();
            alert.show(); // 显示提醒对话框
        } else if (v.getId() == R.id.btn_register) {
            if (phone.length() < 11) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!et_verifycode.getText().toString().equals(mVerifyCode)) {
                Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            } else {
                UserInfo info = new UserInfo();
                info.phone = phone;
                info.password = password;
                mHelper.insert(info);
                Toast.makeText(v.getContext(), "注册成功", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btn_cancel) {
            finish();
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
        }
    }
}

