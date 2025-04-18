package com.example.android.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.android.Fragment.VaccineFragment;
import com.example.android.LoginActivity;
import com.example.android.R;
import com.example.android.entity.HomeList;
import com.example.android.entity.Vaccine;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VaaccineData {
    public static String token;
    private static SharedPreferences sharedPreferences2;

    // 构造器，接收Context对象
    public VaaccineData(Context context) {
        // 获取SharedPreferences实例
        sharedPreferences2 = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        token = sharedPreferences2.getString("token", null);
    }



    //给首页列表提供假数据
//    public static List<Vaccine> getDate() {
////        list.add(new Vaccine("狂犬疫苗", token, R.mipmap.ic_launcher));
////        list.add(new Vaccine("狂犬疫苗", "治疗狂犬病", R.mipmap.ic_launcher));
////        list.add(new Vaccine("狂犬疫苗", "治疗狂犬病", R.mipmap.ic_launcher));
////        list.add(new Vaccine("狂犬疫苗", "治疗狂犬病", R.mipmap.ic_launcher));
////        list.add(new Vaccine("狂犬疫苗", "治疗狂犬病", R.mipmap.ic_launcher));
////        list.add(new Vaccine("狂犬疫苗", "治疗狂犬病", R.mipmap.ic_launcher));
////        list.add(new Vaccine("狂犬疫苗", "治疗狂犬病", R.mipmap.ic_launcher));
////        list.add(new Vaccine("狂犬疫苗", "治疗狂犬病", R.mipmap.ic_launcher));
////        list.add(new Vaccine("狂犬疫苗", "治疗狂犬病", R.mipmap.ic_launcher));
////        list.add(new Vaccine("狂犬疫苗", "治疗狂犬病", R.mipmap.ic_launcher));
////        list.add(new Vaccine("狂犬疫苗", "治疗狂犬病", R.mipmap.ic_launcher));
////        return list;
//    }
}
