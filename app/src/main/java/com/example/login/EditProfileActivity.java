package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

public class EditProfileActivity extends AppCompatActivity {
    private EditText etNickname,etAccount,etSchool,etSign;
    private TextView tvBirthday;
    private AppCompatSpinner acsCity;
    private RadioButton rbGirl,rbBoy;
    private String[] cities;
    private int selectedCityPosition;
    private  String selectedCity;
    private static final String TAG = "tag";
    private String birthday;
    private String birthdayTime;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initView();
        initData();
        initEvent();
    }




    private void initEvent() {
        acsCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCityPosition = i;
                selectedCity = cities[i];
                Log.d(TAG, "onItemSelected: ------------"+i);
                Log.d(TAG, "onItemSelected: ------------"+selectedCity);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tvBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int realMonth = i1+1;
                        birthday = i+"年"+realMonth+"月"+i2+"日";
                        Log.d(TAG, "onItemSelected: ------------"+birthday);
                        popTimePick();
                    }
                },2000,10,23).show();
            }
        });


    }

    private void popTimePick() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                birthdayTime = birthday + i +"时"+i1+"分";
                Log.d(TAG,"------------birthdayTime------"+birthdayTime);
                tvBirthday.setText(birthdayTime);
            }
        },12,36,true).show();
    }

    private void initData() {
        cities = getResources().getStringArray(R.array.cities);
        getDataFromSpf();

    }
    private void getDataFromSpf(){
        SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
        String account = spfRecord.getString("account", "");
        String nickName = spfRecord.getString("nick_name", "");
        String age = spfRecord.getString("age", "");
        String gender = spfRecord.getString("gender", "");
        String city = spfRecord.getString("city", "");
        String school = spfRecord.getString("school", "");
        String home = spfRecord.getString("home", "");
        String sign = spfRecord.getString("sign", "");
        String birthdayTime = spfRecord.getString("birthday_time", "");

        etAccount.setText(account);
        etNickname.setText(nickName);
        etSchool.setText(school);
        etSign.setText(sign);
        tvBirthday.setText(birthdayTime);
        if(TextUtils.equals("男",gender)){
            rbBoy.setChecked(true);
        }
        if(TextUtils.equals("女",gender)){
            rbBoy.setChecked(true);
        }

        for(int i = 0;i<cities.length;i++){

            if(TextUtils.equals(cities[i],city)){
                selectedCityPosition = i;
                break;
            }
        }
        acsCity.setSelection(selectedCityPosition);




    }



    private void initView() {
        etAccount = findViewById(R.id.et_account_text);
        etNickname = findViewById(R.id.et_nick_name_text);
        etSchool = findViewById(R.id.et_school_text);
        etSign = findViewById(R.id.et_sign_text);
        tvBirthday = findViewById(R.id.tv_birth_time_text);
        acsCity = findViewById(R.id.acs_home_text);
        rbBoy = findViewById(R.id.rb_boy);
        rbGirl = findViewById(R.id.rb_girl);

    }


    public void save(View view) {
        /*
        * SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
        String account = spfRecord.getString("account", "");
        String nickName = spfRecord.getString("nick_name", "");
        String age = spfRecord.getString("age", "");
        String gender = spfRecord.getString("gender", "");
        String city = spfRecord.getString("city", "");
        String school = spfRecord.getString("school", "");
        String home = spfRecord.getString("home", "");
        String sign = spfRecord.getString("sign", "");
        String birthdayTime = spfRecord.getString("birthday_time", "");
        *
        * */
        String account = etAccount.getText().toString();
        String sign = etSign.getText().toString();
        String school = etSchool.getText().toString();
        String nick_name = etNickname.getText().toString();

        String gender = "男";
        if(rbBoy.isChecked())gender = "男";
        if(rbGirl.isChecked()) gender = "女";

        SharedPreferences spfRecord = getSharedPreferences("spfRecord",MODE_PRIVATE);
        SharedPreferences.Editor edit = spfRecord.edit();
        edit.putString("account",account);
        edit.putString("sign",sign);
        edit.putString("school",school);
        edit.putString("nick_name",nick_name);
        edit.putString("birthday_time",birthdayTime);
        edit.putString("city",selectedCity);
        edit.putString("home",selectedCity);
        edit.putString("gender",gender);
        edit.commit();

        this.finish();
    }
}