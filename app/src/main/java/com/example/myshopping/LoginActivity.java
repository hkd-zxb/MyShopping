package com.example.myshopping;


import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myshopping.bean.UserInfo;
import com.example.myshopping.database.UserDBHelper;
import com.example.myshopping.util.ViewUtil;

import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll_fragment;
    private RadioGroup rg_login;
    private RadioButton rb_password; // 声明一个单选按钮对象
    private RadioButton rb_verifycode; // 声明一个单选按钮对象
    private RadioButton rb_cancel;
    private EditText et_phone; // 声明一个编辑框对象
    private TextView tv_password; // 声明一个文本视图对象
    private EditText et_password; // 声明一个编辑框对象
    private Button btn_forget; // 声明一个按钮控件对象
    private CheckBox ck_remember; // 声明一个复选框对象
    private int mRequestCode = 0; // 跳转页面时的请求代码
    private boolean isRemember = false; // 是否记住密码
    private String mPassword = "123456"; // 默认密码
    private String mVerifyCode; // 验证码
    private UserDBHelper mHelper; // 声明一个用户数据库的帮助器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rg_login = findViewById(R.id.rg_login);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        rb_cancel = findViewById(R.id.rb_cancel);
        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        ll_fragment = findViewById(R.id.ll_fragment);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_login_more).setOnClickListener(this);
        // 给rg_login设置单选监听器
        rg_login.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_password) {
                    ll_fragment.setVisibility(View.GONE);
                    tv_password.setText("登录密码：");
                    et_password.setHint("请输入密码");
                    btn_forget.setText("忘记密码");
                    ck_remember.setVisibility(View.VISIBLE);
                    rb_cancel.setChecked(false);

                } else if (checkedId == R.id.rb_verifycode) { // 选择了验证码登录
                    ll_fragment.setVisibility(View.GONE);
                    tv_password.setText("　验证码：");
                    et_password.setHint("请输入验证码");
                    btn_forget.setText("获取验证码");
                    ck_remember.setVisibility(View.GONE);
                    rb_verifycode.setChecked(false);
                } else if (checkedId == R.id.rb_cancel) {
                    ll_fragment.setVisibility(View.GONE);
                    rb_cancel.setChecked(false);
                }
            }
        });
        // 给ck_remember设置勾选监听器
        ck_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getId() == R.id.ck_remember) {
                    isRemember = isChecked;
                }
            }
        });
        // 给et_phone添加文本变更监听器
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        // 给et_password添加文本变更监听器
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));
        btn_forget.setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        // 给密码编辑框注册一个焦点变化监听器，一旦焦点发生变化，就触发监听器的onFocusChange方法
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String phone = et_phone.getText().toString();
                // 判断是否是密码编辑框发生焦点变化
                if (v.getId() == R.id.et_password && isRemember) {
                    // 用户已输入手机号码，且密码框获得焦点
                    if (phone.length() > 0 && hasFocus) {
                        // 根据手机号码到数据库中查询用户记录
                        UserInfo info = mHelper.queryByPhone(phone);
                        if (info != null) {
                            // 找到用户记录，则自动在密码框中填写该用户的密码
                            et_password.setText(info.password);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        if (v.getId() == R.id.btn_forget) { // 点击了“忘记密码”按钮
            if (phone.length() < 11) { // 手机号码不足11位
                Toast.makeText(this, "请输入正确的手机号,手机号码不足11位", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rb_password.isChecked()) { // 选择了密码方式校验，此时要跳到找回密码页面
                // 以下携带手机号码跳转到找回密码页面
                Intent intent = new Intent(this, LoginForgetActivity.class);
                intent.putExtra("phone", phone);
                startActivityForResult(intent, mRequestCode); // 携带意图返回上一个页面

//                startActivityForResult(); // 携带意图返回上一个页面
            } else if (rb_verifycode.isChecked()) { // 选择了验证码方式校验，此时要生成六位随机数字验证码
                // 生成六位随机数字的验证码
                mVerifyCode = String.format("%06d", new Random().nextInt(999999));
                // 以下弹出提醒对话框，提示用户记住六位验证码数字
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住验证码");
                builder.setMessage("手机号" + phone + "，本次验证码是" + mVerifyCode + "，请输入验证码");
                builder.setPositiveButton("好的", null);
                AlertDialog alert = builder.create();
                alert.show(); // 显示提醒对话框
            }
        } else if (v.getId() == R.id.btn_login) { // 点击了“登录”按钮
            if (phone.length() < 11) { // 手机号码不足11位
                Toast.makeText(this, "请输入正确的手机号手机号码不足11位", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rb_password.isChecked()) { // 密码方式校验
                UserInfo info = mHelper.queryByPhone(phone);
                if (info != null) {
                    if (!info.password.equals(et_password.getText().toString())) {
                        Toast.makeText(this, "密码错误" + info.password, Toast.LENGTH_SHORT).show();
                    } else
                        // 密码校验通过
                        loginSuccess(); // 提示用户登录成功
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("最普通dialog")
                            .setMessage("该手机号尚未注册,是否注册").setPositiveButton("注册", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    register();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.create().show();
                    Toast.makeText(this, "该手机号尚未注册", Toast.LENGTH_SHORT).show();
                }
            } else if (rb_verifycode.isChecked()) { // 验证码方式校验
                if (!et_password.getText().toString().equals(mVerifyCode)) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                } else { // 验证码校验通过
                    loginSuccess(); // 提示用户登录成功
                }
            }
        } else if (v.getId() == R.id.btn_login_more) {
            ll_fragment.setVisibility(View.VISIBLE);
//            getSupportFragmentManager().beginTransaction().add(R.id.ll_fragment,new LoginFragment()).commit();
            ObjectAnimator.ofFloat(ll_fragment, "alpha", 0f, 1f).setDuration(1000).start();

//            ll_fragment.animate().alpha(1f).setDuration(1000).start();
        } else if (v.getId() == R.id.btn_register) {
            register();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCode && data != null) {
            // 用户密码已改为新密码，故更新密码变量
            mPassword = data.getStringExtra("new_password");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        et_password.setText("");
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

    private void loginSuccess() {
        // 如果勾选了“记住密码”，则把手机号码和密码保存为数据库的用户表记录
        if (isRemember) {
            UserInfo info = new UserInfo(); // 创建一个用户信息对象
            info.phone = et_phone.getText().toString();
            info.password = et_password.getText().toString();
            mHelper.insert(info); // 往用户数据库添加登录成功的用户信息
        }
        startActivity(new Intent(this, DepartmentStoreActivity.class));

    }

    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    private class HideTextWatcher implements TextWatcher {
        private EditText mView; // 声明一个编辑框对象
        private int mMaxLength; // 声明一个最大长度变量

        public HideTextWatcher(EditText v, int maxLength) {
            super();
            mView = v;
            mMaxLength = maxLength;
        }

        // 在编辑框的输入文本变化前触发
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        // 在编辑框的输入文本变化时触发
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        // 在编辑框的输入文本变化后触发
        public void afterTextChanged(Editable s) {
            String str = s.toString(); // 获得已输入的文本字符串
            // 输入文本达到11位（如手机号码），或者达到6位（如登录密码）时关闭输入法
            if ((str.length() == 11 && mMaxLength == 11)
                    || (str.length() == 6 && mMaxLength == 6)) {
                ViewUtil.hideOneInputMethod(LoginActivity.this, mView); // 隐藏输入法软键盘
            }
        }


    }
}