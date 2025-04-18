package com.example.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
// 正确导入和引用
import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MainActivityTestMap extends AppCompatActivity {
    private String addr;
    private int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private LocationClient locationClient;
    private Button button;
    private TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocationClient.setKey("rU2gcyaeiRpERAsLlWvL6uZn73a9ERRC");
        LocationClient.setAgreePrivacy(true);
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        try {
            locationClient = new LocationClient(context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setContentView(R.layout.activity_main_test_map);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchcity();
            }
        });




    }
    public String fetchcity(){
        // 检查是否需要解释权限用途（可选）
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        )) {
            // 显示解释对话框（例如：弹窗说明需要定位的原因）
            new AlertDialog.Builder(this)
                    .setTitle("需要定位权限")
                    .setMessage("请允许定位权限以使用地图和导航功能")
                    .setPositiveButton("确定", (dialog, which) -> {
                        // 再次请求权限
                        ActivityCompat.requestPermissions(
                                this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE
                        );
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            // 直接请求权限
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }

        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setIsNeedAddress(true);
        locationClient.setLocOption(locationClientOption);

        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                addr = bdLocation.getCity();
                int code = bdLocation.getLocType();
                Log.d("codeabc", code+"");
                textView.setText(addr);
            }
        });
        locationClient.start();
        return addr;
    }
}