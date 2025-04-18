package com.example.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.android.Fragment.Dialog;
import com.example.android.entity.Vaccine;
import com.example.android.entity.VaccineRespone;
import com.example.android.tool.BASEURL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VaccineDetailActivity extends AppCompatActivity {
    private SharedPreferences fetchuser;
    private Integer count = 0;
    private TextView vaccineNameTextView;
    private TextView vaccineDatile;
    private ImageView vaccpic;
    private Button orderbtn;
    private Dialog dialog;
    private String Vname = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_detail);

        // 获取传递过来的vaccine_id
        Integer vaccineId = getIntent().getIntExtra("vaccine_id", 0);
        Log.d("vaccid", vaccineId.toString());
        fetchuser = getSharedPreferences("user",MODE_PRIVATE);


        // 显示详细信息
        vaccpic = findViewById(R.id.vaccpic);
        vaccineDatile = findViewById(R.id.vaccinedatile);
        vaccineNameTextView = findViewById(R.id.vaccinname);
        orderbtn = findViewById(R.id.order_btn);
        getVaccineById(vaccineId, new VaccineCountCallback() {
            @Override
            public void count(Integer count) {
                    //    预定按钮点击方法
                    orderbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            判断有无登录
                            if(fetchuser.getString("username",null) == null)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplication(),"未登录，请登陆后使用该功能",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });

                            }else {
                                if(count!=0){
                                    ////                打开对话框
                                    try {
                                        searchvaccinebyid(vaccineId, new VaccineNameCallback() {
                                            @Override
                                            public void onSuccess(String vaccineName, Integer vaccid) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog = new Dialog(getApplication(),vaccineName,vaccid);
                                                        dialog.show(getSupportFragmentManager(),"orderdialog");
                                                        Log.d("n", vaccineName);
                                                    }
                                                });
                                            }
                                        });
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplication(),"此疫苗以无库存，请改日预约",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                        }
                    });
            }
        });



    }

    // 假设有一个方法来获取Vaccine对象
    private void getVaccineById(Integer vaccineId,VaccineCountCallback callback) {
        // 这里你需要实现从数据源中获取Vaccine对象的逻辑
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                String baseurl = BASEURL.getBase_URL()+ "vaccine/searchbyid/";
                String fullurl = baseurl + vaccineId;
                Log.d("fullurl", fullurl.toString());

                Request request = new Request.Builder()
                        .url(fullurl)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(VaccineDetailActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String responseData = response.body().string();
                        // 假设服务器返回的是JSON格式的列表
                        JsonObject root = JsonParser.parseString(responseData).getAsJsonObject();
                        String name = root.getAsJsonObject("data").get("vaccine_name").getAsString();
                        String datile = root.getAsJsonObject("data").get("vaccine_datil").getAsString();
                        count = root.getAsJsonObject("data").get("vaccine_count").getAsInt();
                        callback.count(count);
                        try {
                            String picurl = root.getAsJsonObject("data").get("vaccine_pic").getAsString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(VaccineDetailActivity.this)
                                            .load("http://192.168.1.101:8080/api/file/" + picurl)
                                            .error(R.mipmap.ic_launcher)
                                            .into(vaccpic);
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(VaccineDetailActivity.this).load(R.mipmap.ic_launcher).into(vaccpic);
                                }
                            });
                            System.err.println(e + "");
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                vaccineNameTextView.setText(name);
                                vaccineDatile.setText(datile);
                                Log.d("vaname", name);
                                Log.d("vdatile", datile);

                            }
                        });
                    }
                });
            }
        }).start();
    }

//    获取疫苗名字
//    通过疫苗id查疫苗
public void searchvaccinebyid(Integer Vaccineid,VaccineNameCallback callback) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.1.101:8080/api/vaccine/searchbyid/"+Vaccineid)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String res = response.body().string();
                        JsonObject body = JsonParser.parseString(res).getAsJsonObject();
                        Vname = body.getAsJsonObject("data").get("vaccine_name").getAsString();
                        Integer Vid = body.getAsJsonObject("data").get("vaccine_id").getAsInt();
                        callback.onSuccess(Vname,Vid);
                    }
                });
            }
        }).start();
}

//获取疫苗名称回调接口
    public interface VaccineNameCallback {
        void onSuccess(String vaccineName,Integer vaccid);
    }

    public interface VaccineCountCallback{
        void count(Integer count);
    }

}