package com.example.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.entity.UserInfo;
import com.example.android.tool.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringBufferInputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText LoginUsername;
    private EditText LoginPassword;
    private CheckBox RememberPassowrd;
    private TextView Register;
    private Button LoginButton;
    private boolean islogin;

    private boolean isislogin = true;
    private SharedPreferences sharedPreferences, sharedPreferences2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManager s = new SessionManager(LoginActivity.this);
        setContentView(R.layout.activity_login);
        LoginUsername = findViewById(R.id.login_username);
        LoginPassword = findViewById(R.id.login_password);
        RememberPassowrd = findViewById(R.id.login_rememberpassword);
        Register = findViewById(R.id.register);
        LoginButton = findViewById(R.id.Login_Button);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        sharedPreferences2 = getSharedPreferences("user", MODE_PRIVATE);
        if(s.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            LoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dologin();
                }
            });

//        跳转注册页面
            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });


//        是否记住了密码
            islogin = sharedPreferences.getBoolean("islogin", false);
            if (islogin) {
                String username = sharedPreferences.getString("username", null);
                String password = sharedPreferences.getString("password", null);

                LoginUsername.setText(username);
                LoginPassword.setText(password);
                RememberPassowrd.setChecked(true);
            }

            RememberPassowrd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    islogin = isChecked;
                }
            });
        }


    }


    //    执行登录操作
    private void dologin() {
        String username = LoginUsername.getText().toString();
        String password = LoginPassword.getText().toString();
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
        }else{
            //        创建前端传到后端的数据
            String jsonString = "";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_name", username);
                jsonObject.put("password", password);
                jsonString = jsonObject.toString();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
//创建HTTP连接
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(jsonString, MediaType.parse("application/json"));
            Request request = new Request.Builder()
                    .url("http://192.168.1.101:8080/api/admin/login")
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                //            respones返回不成功的方法
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //            respones返回成功的方法
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("loginres", response+"");
//                获取返回体里的code和msg内容
                    String respon = response.body().string();
                    try {
                        JSONObject PANDCODE = new JSONObject(respon);
                        String code = PANDCODE.getString("code");
                        String msg = PANDCODE.getString("msg");


                        if (code.equals("0")) {
                            JSONObject data = PANDCODE.getJSONObject("data");
                            String phone = data.getString("phone");
//                        记住密码功能
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", username);
                            editor.putString("password", password);
                            editor.putBoolean("islogin", islogin);
                            editor.commit();
//将登录的用户名存入seesion中,并把登录态改为已登录
                            SessionManager seesion = new SessionManager(LoginActivity.this);
                            seesion.saveUserDetails(username);
                            seesion.setLogin(isislogin);


                            SharedPreferences.Editor editor1 = sharedPreferences2.edit();
                            editor1.putString("username", username);
                            editor1.putString("password", password);
                            editor1.putString("phone",phone);
                            editor1.commit();
                            Log.d("username", sharedPreferences2.getString("username",null)+"");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }


    }


}