package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_TAKE = 1;
    public static final int REQUEST_CODE_CHOOSE = 0;
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
    private Uri imageUri;
    private ImageView ivAvatar;
    private String imageBase64;
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
        String image64 = spfRecord.getString("image_64", "");

        etAccount.setText(account);
        etNickname.setText(nickName);
        etSchool.setText(school);
        etSign.setText(sign);
        tvBirthday.setText(birthdayTime);
        ivAvatar.setImageBitmap(ImageUtil.base64ToImage(image64));
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
        ivAvatar = findViewById(R.id.iv_avatar);
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
        edit.putString("image_64",imageBase64);
        edit.commit();
        this.finish();
    }

    //拍照功能的实现：先在Manifest里声明这个权限
    public void takePhoto(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //真正的执行拍照
            doTake();
        }
        else{
            //去申请权限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                doTake();
            }else{
                Toast.makeText(this, "未获得摄像头的权限nie~", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void doTake() {
        File imageTemp = new File(getExternalCacheDir(),"imageOut.jpg");
        if(imageTemp.exists()){
            imageTemp.delete();
        }
        try{
            imageTemp.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT>24){
            // contentProvider
            imageUri = FileProvider.getUriForFile(this,"com.example.login.fileprovider",imageTemp);
        }else{
            imageUri = Uri.fromFile(imageTemp);
        }
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent, REQUEST_CODE_TAKE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_TAKE){
            if(resultCode == RESULT_OK){
                //获取拍摄的照片
                try{
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ivAvatar.setImageBitmap(bitmap);
                    String imageToBase64 = ImageUtil.imageToBase64(bitmap);
                    imageBase64 = imageToBase64;
                }catch (FileNotFoundException e){

                }
            }
        }

    }
    public void choosePhoto(View view) {

    }
}