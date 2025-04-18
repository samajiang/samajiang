package com.example.android.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.Fragment.VaccineFragment;
import com.example.android.R;
import com.example.android.entity.HomeList;
import com.example.android.entity.Vaccine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VaccineListAdapter extends RecyclerView.Adapter<VaccineListAdapter.MyHolder> {
    //    初始化数据来源列表
    public List<Vaccine> vaccinelist;
    private OnItemClickListener onItemClickListener;

    public VaccineListAdapter(List<Vaccine> vaccineList) {
        this.vaccinelist = vaccineList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //        加载布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccine_list_item, null);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

//绑定数据
        Vaccine vaccine = vaccinelist.get(position);

        holder.vaccinename.setText(vaccine.getVaccine_name());
        holder.vaccinedetail.setText(vaccine.getVaccine_datil());
        //        通过后台返回的疫苗的时间戳去调用后台接口获取不同疫苗的图片
//        判断查询到的疫苗信息中图片链接是否为空，为空就给个占位图
        String flag = vaccine.getVaccine_pic();
        String url = "http://192.168.1.103:8080/api/file/";
        String fullurl = url + flag;
        if (flag != null) {
            Glide.with(holder.itemView.getContext()).load(fullurl).into(holder.vaccinepic);
        } else {
            Glide.with(holder.itemView.getContext()).load(R.mipmap.ic_launcher).into(holder.vaccinepic);
        }

        // 设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(vaccine);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return vaccinelist.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        //        初始化Recycler中的组件

        ImageView vaccinepic;
        TextView vaccinename;
        TextView vaccinedetail;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            vaccinepic = itemView.findViewById(R.id.vaccinePic);
            vaccinename = itemView.findViewById(R.id.vaccinename);
            vaccinedetail = itemView.findViewById(R.id.vaccineDetail);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Vaccine vaccine);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
