package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {
    private TextView tvNickname,tvAccount,tvAge,tvGender,tvCity,tvHome,tvSchool,tvSign,tvBirthdayTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initView();

    }

    @Override
    protected void onResume() {//保存后退出到User界面，不会执行onCreate，为了加载数据，将initDate放置在onResume里，onResume是一定要走的！！生命周期
        super.onResume();
        initDate();
    }



    public void toEdit(View view) {
        Intent intent = new Intent(this,EditProfileActivity.class);

        startActivity(intent);
    }
   //logout直接用
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

    private void initView(){
        tvAccount = findViewById(R.id.tv_account_text);
        tvNickname = findViewById(R.id.tv_nick_name);
        tvAge = findViewById(R.id.tv_age);
        tvGender = findViewById(R.id.tv_gender);
        tvCity = findViewById(R.id.tv_city);
        tvSchool = findViewById(R.id.tv_school_text);
        tvHome = findViewById(R.id.tv_home_text);
        tvSign = findViewById(R.id.tv_sign_text);
        tvBirthdayTime = findViewById(R.id.tv_birth_time_text);
    }
    private  void initDate(){
        getDataFromSpf();
    }

    //get数据并显示在textView里
    private void getDataFromSpf(){
        SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
        String account = spfRecord.getString("account", "");
        String nickName = spfRecord.getString("nick_name", "");
        String gender = spfRecord.getString("gender", "");
        String city = spfRecord.getString("city", "");
        String school = spfRecord.getString("school", "");
        String home = spfRecord.getString("home", "");
        String sign = spfRecord.getString("sign", "");
        String birthdayTime = spfRecord.getString("birthday_time", "");
        String age = getAgeByBirthdayTime(birthdayTime);

        tvAccount.setText(account);
        tvNickname.setText(nickName);
        tvAge.setText(age);
        tvGender.setText(gender);
        tvCity.setText(city);
        tvSchool.setText(school);
        tvHome.setText(home);
        tvSign.setText(sign);
        tvBirthdayTime.setText(birthdayTime);

    }

    private String getAgeByBirthdayTime(String birthdayTime) {
        //2000年10月……
        if(TextUtils.isEmpty(birthdayTime)){
            return "";
        }
        try{
            int index = birthdayTime.indexOf("年");
            String result = birthdayTime.substring(0, index);
            int parseInt = Integer.parseInt(result);
            return String.valueOf(2022-parseInt) ;
        }catch(Exception e){
            e.printStackTrace();
        }

        return"";
    }

}