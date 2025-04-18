package com.example.android;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import android.Manifest; // Java
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.android.Fragment.BookVaccineFragment;
import com.example.android.Fragment.HomeFragment;
import com.example.android.Fragment.MineFragment;
import com.example.android.Fragment.SearchFragment;
import com.example.android.Fragment.VaccineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Set<String> yiyuanlist = new HashSet<>();
    private PoiSearch poiSearch;
    private SharedPreferences localtionpfr;
    private int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private LocationClient locationClient;

    private MineFragment mineFragment;
    private HomeFragment homeFragment;
    private VaccineFragment vaccineFragment;
    private BookVaccineFragment bookVaccineFragment;
    private SearchFragment searchFragment;
    public BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        初始化百度地图定位SDK
        LocationClient.setKey("rU2gcyaeiRpERAsLlWvL6uZn73a9ERRC");
        LocationClient.setAgreePrivacy(true);
        super.onCreate(savedInstanceState);
        Context appconten = getApplicationContext();
//        初始化百度地图poi搜索SDK
        // 先设置隐私合规（必须在初始化前调用）
        SDKInitializer.setAgreePrivacy(appconten, true);
        // 使用正确的上下文初始化
        SDKInitializer.initialize(appconten);
        try {
            locationClient = new LocationClient(appconten);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomnavigation);

        localtionpfr = getSharedPreferences("addr", MODE_PRIVATE);

        poiSearch = PoiSearch.newInstance();

        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if(poiResult.getAllPoi()==null){
                    return;
                }
                List<PoiInfo> poiInfos = poiResult.getAllPoi();
                for (int i = 0; i < poiInfos.size(); i++) {
                    PoiInfo poiInfo = poiInfos.get(i);
                    String yiyuanname = poiInfo.name;
                    yiyuanlist.add(yiyuanname);
                }
                Set<String> xxx = yiyuanlist;
                SharedPreferences.Editor editor = localtionpfr.edit();
                editor.putStringSet("addrlist", yiyuanlist);
                editor.apply();
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });

        fetchlocalcity();

//        底部导航栏点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home) {
                    selectFragment(0);
                }
                if (item.getItemId() == R.id.vaccine) {
                    selectFragment(1);
                }
                if (item.getItemId() == R.id.bookvaccine) {
                    selectFragment(2);
                }
                if (item.getItemId() == R.id.search) {
                    selectFragment(3);
                }
                if (item.getItemId() == R.id.mine) {
                    selectFragment(4);
                }
                return true;
            }
        });

//        默认显示的fragment
        selectFragment(0);
    }

    public void selectFragment(int postion) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hidFragment(fragmentTransaction);

        if (postion == 0) {
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
                fragmentTransaction.add(R.id.fragment_content, homeFragment);

            } else {
                fragmentTransaction.show(homeFragment);
            }
        }

        if (postion == 1) {
            if (vaccineFragment == null) {
                vaccineFragment = new VaccineFragment();
                fragmentTransaction.add(R.id.fragment_content, vaccineFragment);

            } else {
                fragmentTransaction.show(vaccineFragment);
            }
        }

        if (postion == 2) {
            if (bookVaccineFragment == null) {
                bookVaccineFragment = new BookVaccineFragment();
                fragmentTransaction.add(R.id.fragment_content, bookVaccineFragment);
            } else {
                fragmentTransaction.show(bookVaccineFragment);
            }
        }

        if (postion == 3) {
            if (searchFragment == null) {
                searchFragment = new SearchFragment();
                fragmentTransaction.add(R.id.fragment_content, searchFragment);
            } else {
                fragmentTransaction.show(searchFragment);
            }
        }

        if (postion == 4) {
            if (mineFragment == null) {
                mineFragment = new MineFragment();
                fragmentTransaction.add(R.id.fragment_content, mineFragment);
            } else {
                fragmentTransaction.show(mineFragment);
            }
        }


        fragmentTransaction.commit();
    }

    private void hidFragment(FragmentTransaction fragmentTransaction) {

        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }
        if (vaccineFragment != null) {
            fragmentTransaction.hide(vaccineFragment);
        }
        if (bookVaccineFragment != null) {
            fragmentTransaction.hide(bookVaccineFragment);
        }
        if (searchFragment != null) {
            fragmentTransaction.hide(searchFragment);

        }

    }

    public void fetchlocalcity() {

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

        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String a = bdLocation.getCity();
                poiSearch.searchInCity(new PoiCitySearchOption()
                        .city(a)
                        .pageNum(0)
                        .keyword("医院"));
            }
        });
        locationClient.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        yiyuanlist.clear();
        SharedPreferences.Editor editor = localtionpfr.edit();
        editor.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        yiyuanlist.clear();
        SharedPreferences.Editor editor = localtionpfr.edit();
        editor.clear();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        yiyuanlist.clear();
        SharedPreferences.Editor editor = localtionpfr.edit();
        editor.clear();
    }

    // 自定义方法：重启应用
    public void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finishAffinity();
    }
}