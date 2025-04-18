package com.example.android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.Data.NewData;
import com.example.android.R;
import com.example.android.entity.HomeList;
import com.example.android.entity.NewsItem;
import com.example.android.entity.Vaccine;

import java.util.ArrayList;
import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyHolder> {

    //    初始化数据来源列表
    private List<NewsItem> newsLists = new ArrayList<>();

    private OnnewsItemClickListener onnewsItemClickListener;

    //    设置RecyclerView列表数据
    public void setData(List<NewsItem> lists) {
        this.newsLists = lists;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        加载布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
//绑定数据
        NewsItem newsList = newsLists.get(position);

        Glide.with(holder.itemView.getContext()).load(newsList.getThumbnail()).placeholder(R.mipmap.ic_launcher).into(holder.newpic);
        holder.newtile.setText(newsList.getTitle());
        holder.newdatil.setText(newsList.getAuthor());
        //            设置新闻点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onnewsItemClickListener != null) {
                    onnewsItemClickListener.onitemClick(newsList);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
//        返回数据源列表的大小(多少条数据)
        return newsLists.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

//        初始化Recycler中的组件

        ImageView newpic;
        TextView newtile;
        TextView newdatil;


        public MyHolder(@NonNull View itemView) {
            super(itemView);


            newpic = itemView.findViewById(R.id.NewPic);
            newtile = itemView.findViewById(R.id.Title);
            newdatil = itemView.findViewById(R.id.NewDetail);


        }
    }
   public interface OnnewsItemClickListener{
        void onitemClick(NewsItem newsItem);
   }

   public void setOnItemClickListener(OnnewsItemClickListener listener){
        this.onnewsItemClickListener = listener;
   }
}
