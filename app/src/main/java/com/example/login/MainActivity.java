package com.example.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_REGIST = 1;
    private Button btnLogin;
    private EditText etAccount,etPassword;
    private CheckBox cbRemember;
    private CheckBox cbAutoLogin;

    private String userName ;
    private String pass ;


    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("登录");

        initView();


        //*******数据的初始化，跟后端有关！
        initData();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();

                //提醒还没注册账号
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(MainActivity.this, "还没注册账号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.equals(account,userName)){
                    if(TextUtils.equals(password,pass)){
                        Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                        if(cbRemember.isChecked()){
                            SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
                            SharedPreferences.Editor edit = spf.edit();
                            edit.putString("account",account);
                            edit.putString("password",password);
                            edit.putBoolean("isRemember",true);
                            if(cbAutoLogin.isChecked()){
                                edit.putBoolean("isLogin",true);
                            }else{
                                edit.putBoolean("isLogin",false);
                            }
                            edit.apply();

                        }else{
                            SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
                            SharedPreferences.Editor edit = spf.edit();
                            edit.putBoolean("isRemember",false);
                            edit.apply();
                        }
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, HomePageActivity.class);
                        intent.putExtra("account",account);
                        startActivity(intent);
                        MainActivity.this.finish();


                    }else{
                        Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"用户名错误",Toast.LENGTH_LONG).show();
                }

            }
        });

        //当自动登录确认时，记住密码也确认
        cbAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbRemember.setChecked(true);
                }
            }
        });

        //当记住密码取消时，自动登录也取消
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cbAutoLogin.setChecked(false);
                }
            }
        });

    }

    private void initData() {
        SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);

        //******向服务器发送请求，从数据库获得是否记住密码和是否登录，就是下面的初始状态要改变，我这边默认了FALSE，其实是不对的，你改一改
        boolean isRemember = spf.getBoolean("isRemember", false);
        boolean isLogin = spf.getBoolean("isLogin", false);

        //******向服务器发送请求，从数据库获得是原来的账号和原来的密码，就是下面的初始状态要改变，我这边默认了空字符串，其实是不对的，你改一改
        String account = spf.getString("account", "");
        String password = spf.getString("password", "");

        if(isLogin){
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
            MainActivity.this.finish();
        }

        userName = account;
        pass = password;

        if(isRemember){
            etAccount.setText(account);
            etPassword.setText(password);
            cbRemember.setChecked(true);
        }

    }

    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        cbRemember = findViewById(R.id.cb_remember);
        cbAutoLogin = findViewById(R.id.cb_auto_login);
    }


    public void jumoToRegister(View view) {
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivityForResult(intent,REQUEST_CODE_REGIST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGIST && resultCode == RegisterActivity.RESULT_CODE_REGIST && data != null) {
            //接收从注册界面传来的包裹
            Bundle extras = data.getExtras();

            String account = extras.getString("account", "");
            String password = extras.getString("password", "");

            etAccount.setText(account);
            etPassword.setText(password);

            userName = account;
            pass = password;

        }
    }
}