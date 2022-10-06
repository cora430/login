package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {
    private TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        tvContent = findViewById(R.id.tv_content);
        Intent intent = getIntent();
        String account = intent.getStringExtra("account");
        tvContent.setText("欢迎你："+account);
    }
    public void logout(View view) {
        //点击退出登录按钮时，isLogin状态改为FALSE，防止直接又自动登陆了
        SharedPreferences spf = getSharedPreferences("spfRecord", MODE_PRIVATE);
        SharedPreferences.Editor edit = spf.edit();
        edit.putBoolean("isLogin", false);
        edit.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}


