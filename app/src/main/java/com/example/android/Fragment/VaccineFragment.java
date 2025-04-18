package com.example.android.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.Adapter.VaccineListAdapter;
import com.example.android.tool.BASEURL;
import com.example.android.R;
import com.example.android.VaccineDetailActivity;
import com.example.android.entity.Vaccine;
import com.example.android.entity.VaccineRespone;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class VaccineFragment extends Fragment {
    private List<Vaccine> vaccineList = new ArrayList<>();
    private RecyclerView vaccinelistitem;
    private VaccineListAdapter vaccineListAdapter;

    private SharedPreferences sharedPreferences2;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_vaccine, container, false);
        //        初始化控件的地方
        sharedPreferences2 = getActivity().getSharedPreferences("user",MODE_PRIVATE);
//        列表
        vaccinelistitem = rootview.findViewById(R.id.vaccin_listitem);
        swipeRefreshLayout = rootview.findViewById(R.id.vaccine_refresh);
//        记得使用下拉刷新的时候要先获取数据
        loadVaccinesFromServer();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadVaccinesFromServer();
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

    private void loadVaccinesFromServer() {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                String url = BASEURL.getBase_URL()+ "vaccine/searchvaccine"; // 替换为您的服务器URL

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseData = response.body().string();

                            // 假设服务器返回的是JSON格式的列表
                            Type listType = new TypeToken<VaccineRespone>() {
                            }.getType();
                            VaccineRespone vaccineRespone = new Gson().fromJson(responseData, listType);
                            List<Vaccine> abc = vaccineRespone.getData();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // 更新RecyclerView的适配器
                                    vaccineListAdapter = new VaccineListAdapter(abc);
                                    vaccinelistitem.setAdapter(vaccineListAdapter);

                                    // 设置点击事件监听器
                                    vaccineListAdapter.setOnItemClickListener(new VaccineListAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(Vaccine vaccine) {
                                            // 跳转到详情页
                                            Intent intent = new Intent(getActivity(), VaccineDetailActivity.class);
                                            intent.putExtra("vaccine_id", vaccine.getVaccine_id());
                                            startActivity(intent);
                                        }
                                    });
                                }
                            });

                        } else {
                            System.out.println("shibai");
                        }
                    }
                });
            }
        }).start();

    }




}