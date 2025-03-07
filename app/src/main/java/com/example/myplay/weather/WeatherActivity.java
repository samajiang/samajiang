package com.example.myplay.weather;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myplay.R;

public class WeatherActivity extends AppCompatActivity {
    static final String API_KEY = "f01e9e0637df5d4a015d664fc576c7a7";
    static final String BASE_URL = "http://apis.juhe.cn/simpleWeather/query";
    private EditText searchkuang;
    private Button searchbtn;
    private TextView getcity, gettemperature, weather, wind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initview();
        searchbtn();
    }

    //    初始化控件
    private void initview() {
        searchkuang = findViewById(R.id.search_ed);
        searchbtn = findViewById(R.id.search_weather);
        getcity = findViewById(R.id.tv_city);
        gettemperature = findViewById(R.id.tv_temperature);
        weather = findViewById(R.id.tv_weather);
        wind = findViewById(R.id.tv_wind);
    }

    //查询天气按钮点击事件
    private void searchbtn() {
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dif = searchkuang.getText().toString().trim();//去除首尾空格
                if (!dif.isEmpty()) {
//                执行获取天气信息代码,启动异步任务执行网络请求
                    new WeatherTask(WeatherActivity.this).execute(dif);
                }
            }
        });
    }
//更新UI
    void updataWeatherUI(String city, String temperature, String info, String power) {
        getcity.setText(city);
        gettemperature.setText(String.format("温度：%s℃", temperature));
        weather.setText(String.format("天气：%s", info));
        wind.setText(String.format("风力：%s", power));


    }

}