package com.example.android.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.MyHolder> {

    public List<String> HistoryList;

    public SearchHistoryAdapter(List<String> HistoryList) {
        this.HistoryList = HistoryList;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyitem, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String searchHistory = HistoryList.get(position);
        holder.his.setText(searchHistory);
    }

    @Override
    public int getItemCount() {
        return HistoryList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        TextView his;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            his = itemView.findViewById(R.id.lishi);
        }
    }

    public void update(List<String> histoty) {
        this.HistoryList = histoty;
        notifyDataSetChanged();
    }
}
