package com.example.android.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.android.Adapter.SearchHistoryAdapter;
import com.example.android.R;
import com.example.android.SearchActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private SearchHistoryAdapter searchHistoryAdapter;
    private SharedPreferences searchhistory1;

    private Button buttonclear;
    private List<String> histoty;

    private RecyclerView historyview;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_search, container, false);
        historyview = rootview.findViewById(R.id.history_recyclerview);
        searchhistory1 = getActivity().getSharedPreferences("history1", Context.MODE_PRIVATE);
        searchView = rootview.findViewById(R.id.searchkuang);
        Set<String> his = gethistory();
        histoty = new ArrayList<>(his);
        buttonclear = rootview.findViewById(R.id.clear_history);
        searchHistoryAdapter = new SearchHistoryAdapter(histoty);
        historyview.setAdapter(searchHistoryAdapter);

        buttonclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearallhistory();
                Set<String> def = gethistory();
                histoty = new ArrayList<>(def);
                searchHistoryAdapter.update(histoty);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                跳转具体的搜索界面
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("vaccinename",query.trim());
                Log.d("query", query.trim());
                startActivity(intent);
                savehistory(query);
                Set<String> abc = gethistory();
                histoty = new ArrayList<>(abc);
                searchHistoryAdapter.update(histoty);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return rootview;
    }

    public void savehistory(String query) {
        Set<String> lists = new HashSet<>(searchhistory1.getStringSet("history1", new HashSet<>()));
        lists.add(query);
        SharedPreferences.Editor editor = searchhistory1.edit();
        editor.putStringSet("history1", lists);
        editor.apply();

    }

    public Set<String> gethistory() {
        return searchhistory1.getStringSet("history1", new HashSet<>());
    }

    public void clearallhistory(){
        SharedPreferences.Editor editor = searchhistory1.edit();
        editor.clear();
        editor.apply();
    }
}