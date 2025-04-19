package com.example.android.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.android.Adapter.HomeListAdapter;
import com.example.android.tool.BASEURL;
import com.example.android.NewsDatilActivity;
import com.example.android.R;
import com.example.android.entity.NewsDataresult;
import com.example.android.entity.NewsItem;
import com.example.android.entity.gonggaodata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment {
    private RecyclerView homelistitem;
    private HomeListAdapter homeListAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());

    private Banner banner;

    private String URL = "http://v.juhe.cn/toutiao/index?key=1d313dcf0795b3dbd7ab7d59f45e0bae&type=jiankang";
//    private String BASEURL = "http://192.168.1.101:8080/api/";
    private SwipeRefreshLayout swipeRefreshLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        初始化控件的地方
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = rootview.findViewById(R.id.home_refresh);

//        初始化轮播图组件
        banner = rootview.findViewById(R.id.banner);

        homelistitem = rootview.findViewById(R.id.homelistitem);
//        将列表放入adpter中
        homeListAdapter = new HomeListAdapter();
        homelistitem.setAdapter(homeListAdapter);


        //        使用设置轮播图方法
        setbanner();
        //        列表
//        newsData();

//        下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                newsData();
                setbanner();
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });


        return rootview;
    }

//获取聚合数据中的健康类型的新闻
    public void newsData(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(URL)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        // 1. 获取原始JSON数据
                        String responseData = response.body().string();
                        Log.d("newsdata", "原始响应数据: " + responseData);
                        // 2. 创建Gson实例（推荐使用GsonBuilder）
                        Gson gson = new GsonBuilder().create();
                        // 3. 定义完整的类型信息
                        Type responseType = new TypeToken<NewsDataresult>() {}.getType();
                        // 4. 解析完整数据结构
                        NewsDataresult apiResponse = gson.fromJson(responseData, responseType);
                        // 6. 获取新闻列表
                        List<NewsItem> newsList = apiResponse.getResult().getData();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                homeListAdapter.setData(newsList);
//                                设置点击事件监听器
                                homeListAdapter.setOnItemClickListener(new HomeListAdapter.OnnewsItemClickListener() {
                                    @Override
                                    public void onitemClick(NewsItem newsItem) {
//                                        跳转新闻详情页
                                        Intent intent = new Intent(getActivity(), NewsDatilActivity.class);
                                        intent.putExtra("newsurl",newsItem.getArticleUrl());
                                        intent.putExtra("newstitle",newsItem.getTitle());
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }).start();
    }


//    设置轮播图
    public void setbanner(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(BASEURL.getBase_URL() +"gonggao/fetchgonggao")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String resp = response.body().string();
                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken< List<gonggaodata>>() {}.getType();
                List<gonggaodata> s = gson.fromJson(resp,type);
                Log.d("gonggaodata", s.toString());
                List<String> url = new ArrayList<>()/*BASEURL+"/gonggao"+gonggaoflag*/;
                for(int i = 0;i<s.size();i++){
                    gonggaodata b = s.get(i);
                    String gonggaoflag = b.getGonggao_pic();
                    url.add(BASEURL.getBase_URL()+"gonggao/"+gonggaoflag);
                }
                Log.d("gonggaourllist", url+"");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        banner.setAdapter(new BannerImageAdapter<String>(url) {
                            @Override
                            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                                Glide.with(holder.itemView)
                                        .load(data)
                                        .into(holder.imageView);
                            }
                        });
                        // 开启循环轮播
                        banner.isAutoLoop(true);
                        banner.setScrollBarFadeDuration(1000);
                        banner.setIndicator(new CircleIndicator(getContext()));
                        banner.setIndicatorNormalColor(Color.GRAY);
                        banner.setIndicatorSelectedColor(Color.GREEN);
                        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
                        // 开始轮播
                        banner.start();
                    }
                });
            }
        });

    }
}