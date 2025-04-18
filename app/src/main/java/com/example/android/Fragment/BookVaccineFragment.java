package com.example.android.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.Adapter.OrderAdapter;
import com.example.android.tool.BASEURL;
import com.example.android.R;
import com.example.android.entity.Order;
import com.example.android.entity.OrderRES;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookVaccineFragment extends Fragment {
    String nn = null;
    private RecyclerView recyclerView;
//    private String BASE_URL = "http://192.168.1.101:8080/api/order/";
    private OrderAdapter orderAdapter;
    private SharedPreferences sharedPreferences;
    private SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_book_vaccine, container, false);
//        初始化组件
//        一开始就拿到登录的用户名
        orderAdapter = new OrderAdapter();
        swipeRefreshLayout = rootview.findViewById(R.id.swipe);
        sharedPreferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        nn = sharedPreferences.getString("username", null);
        recyclerView = rootview.findViewById(R.id.order_list);
        if(nn == null){
          orderAdapter.clearData();
          orderAdapter.notifyDataSetChanged();
        }

//        直接new一个监听器并在里边实现删除逻辑
        orderAdapter.setOnItemLongClickListener(new OrderAdapter.onItemLongClick() {
            @Override
            public void onItemLongClick(int position) {
                Order oo = orderAdapter.fetchorderbyposition(position);
                Integer n = oo.getOrder_id();
                Log.d("orderObject", n+"");
                new AlertDialog.Builder(getContext())
                .setTitle("删除预约")
                .setMessage("确认删除？")
                .setPositiveButton("删除",((dialogInterface, i) -> {
                    orderAdapter.deletebyposition(position);
                    deleteorderfromDB(n);
                    Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                }))
                .setNegativeButton("取消",null)
                .show();
            }
        });
        fetchorderinfo(nn);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchorderinfo(nn);
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });



        return rootview;


    }
//    从后端获取预定信息的方法
    public void fetchorderinfo(String user_name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(BASEURL.getBase_URL()+"order/"+user_name)
                        .build();
                Log.d("BASE_URL + user_name", BASEURL.getBase_URL() + user_name);
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "获取数据失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//把获取到的数据放到orderadapter中
                        String res = response.body().string();
                        Type listtype = new TypeToken<OrderRES>() {
                        }.getType();
                        OrderRES orderRES = new Gson().fromJson(res, listtype);
                        List<Order> orders = orderRES.getData();

//                        放入数据到适配器中
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                orderAdapter.setdata(orders);
                                recyclerView.setAdapter(orderAdapter);
                                orderAdapter.notifyDataSetChanged();

                            }
                        });
                    }
                });
            }
        }).start();
    }

//    从后端删除预定信息的方法
    public void deleteorderfromDB(Integer id){
        Log.d("djkashkd", id+"");
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(BASEURL.getBase_URL()+"order/"+"deleteorderbyid/"+id)
                        .delete()
                        .build();
                Log.d("hdhkwhahihodwa", BASEURL.getBase_URL()+"order/"+"deleteorderbyid/"+id);
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"删除失败",Toast.LENGTH_SHORT).show();
                                Log.d("deleteorderfromDB", "失败原因："+e.toString());
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.d("deleteorderfromDB", "删除成功");
                    }
                });
            }
        }).start();
    }


}

