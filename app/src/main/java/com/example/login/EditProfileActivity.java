package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
                        birthday = i+"???"+realMonth+"???"+i2+"???";
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
                birthdayTime = birthday + i +"???"+i1+"???";
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
        if(TextUtils.equals("???",gender)){
            rbBoy.setChecked(true);
        }
        if(TextUtils.equals("???",gender)){
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

        String gender = "???";
        if(rbBoy.isChecked())gender = "???";
        if(rbGirl.isChecked()) gender = "???";

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

    //??????????????????????????????Manifest?????????????????????
    public void takePhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // ????????????????????????
            doTake();
        } else {
            // ???????????????
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                doTake();
            }else{
                Toast.makeText(this, "???????????????????????????nie~", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == 0){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openAlbum();
            }else{
                Toast.makeText(this, "??????????????????????????????nie~", Toast.LENGTH_SHORT).show();
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
                //?????????????????????
                try{
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ivAvatar.setImageBitmap(bitmap);
                    String imageToBase64 = ImageUtil.imageToBase64(bitmap);
                    imageBase64 = imageToBase64;
                }catch (FileNotFoundException e){
                }
            }
        }else if(requestCode == REQUEST_CODE_CHOOSE){
            if(Build.VERSION.SDK_INT < 19){
                handleImageBeforeApi19(data);
            }else{
                handleImageOnApi19(data);
            }
        }
    }

    private void handleImageBeforeApi19(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @TargetApi(19)
    private void handleImageOnApi19(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);

            if (TextUtils.equals(uri.getAuthority(), "com.android.providers.media.documents")) {
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if (TextUtils.equals(uri.getAuthority(), "com.android.providers.downloads.documents")) {
                if (documentId != null && documentId.startsWith("msf:")) {
                    resolveMSFContent(uri, documentId);
                    return;
                }
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                imagePath = getImagePath(contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }

        displayImage(imagePath);
    }
    private void resolveMSFContent(Uri uri, String documentId) {

        File file = new File(getCacheDir(), "temp_file" + getContentResolver().getType(uri).split("/")[1]);

        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);

            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4 * 1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivAvatar.setImageBitmap(bitmap);
            imageBase64 = ImageUtil.imageToBase64(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ivAvatar.setImageBitmap(bitmap);
            String imageToBase64 = ImageUtil.imageToBase64(bitmap);
            imageBase64 = imageToBase64;
        }
    }

    public void choosePhoto(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //?????????????????????
            openAlbum();
        }
        else{
            //???????????????
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);
    }
}