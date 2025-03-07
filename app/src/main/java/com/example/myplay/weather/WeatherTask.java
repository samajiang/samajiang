package com.example.myplay.weather;

import static com.example.myplay.weather.WeatherActivity.API_KEY;
import static com.example.myplay.weather.WeatherActivity.BASE_URL;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherTask extends AsyncTask<String,Void,String> {
    private String TAG;

private WeatherActivity weatherActivity;
//初始化activity，创建一个activity构造方法
    public WeatherTask(WeatherActivity weatherActivity) {
        this.weatherActivity = weatherActivity;
    }

//    Params 城市名称数组（实际只取第一个元素）
    @Override
    protected String doInBackground(String... Params) {

        try {
//参数编码处理
            String encodeedCity = URLEncoder.encode(Params[0],"UTF-8");
//            构建完整url
            String fullUrl = BASE_URL +"?city="+encodeedCity + "&key=" + API_KEY;
//            建立http连接
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(fullUrl)
                    .build();
//执行同步网络请求
            Response response = client.newCall(request).execute();
            Log.d(TAG = "url", fullUrl);
//            返回响应体内容
        return response.body().string();

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

//    处理请求返回的结果
    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);

        if(result != null){

            try{
//                将字符串转为json对象
                JSONObject jsonObject = new JSONObject(result);
                Log.d(TAG = "data", jsonObject.toString());
//                检查API返回码
                if(jsonObject.getInt("error_code") == 0){
//                    解析核心数据
                    JSONObject data = jsonObject.getJSONObject("result");
                    JSONObject realtime = data.getJSONObject("realtime");

//                    将获取到的数据传到更新UI的方法中
                    weatherActivity.updataWeatherUI(
                            data.getString("city"),
                            realtime.getString("temperature"),
                            realtime.getString("info"),
                            realtime.getString("power")
                    );
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}
