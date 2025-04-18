package com.example.android;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android.entity.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText Rigname, Rigpassword, Rgiphone, confirmpassword, Rigage;

    private String sexname;
    private RadioGroup Rigsex;
    private Button RigBtn;
    private Toolbar registertoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Rigname = findViewById(R.id.input_username);
        Rigpassword = findViewById(R.id.input_password);
        Rgiphone = findViewById(R.id.input_phone);
        Rigage = findViewById(R.id.input_age);
        RigBtn = findViewById(R.id.rig_btn);
        confirmpassword = findViewById(R.id.input_confirmpassword);
        registertoolbar = findViewById(R.id.register_toolbar);
        Rigsex = findViewById(R.id.input_sex);

        Rigsex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton sex = findViewById(group.getCheckedRadioButtonId());
                sexname = sex.getText().toString();
            }
        });

        RigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doregister();
            }
        });

//        Toolbar返回按钮
        registertoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void doregister() {
        String rigname = Rigname.getText().toString();
        String rigpwd = Rigpassword.getText().toString();
        String rigphone = Rgiphone.getText().toString();
        String confirmpwd = confirmpassword.getText().toString();
        String age = Rigage.getText().toString();
        String jsonString = "";

        if (TextUtils.isEmpty(rigname)) {
            Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(rigpwd)) {
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(confirmpwd)) {
            Toast.makeText(RegisterActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
        } else if (!rigpwd.equals(confirmpwd)) {
            Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(rigphone)) {
            Toast.makeText(RegisterActivity.this, "请输入电话", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(age)) {
            Toast.makeText(RegisterActivity.this, "请输入年龄", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_name", rigname);
                jsonObject.put("password", rigpwd);
                jsonObject.put("sex", sexname);
                jsonObject.put("age", age);
                jsonObject.put("phone", rigphone);
                jsonString = jsonObject.toString();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            RequestBody body = RequestBody.create(jsonString, MediaType.parse("application/json"));
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://192.168.1.102:8080/api/admin/register")
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    // 回到主线程操纵界面
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    // 回到主线程操纵界面
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            });
        }
    }
}