package com.example.android.Data;

import com.example.android.R;
import com.example.android.entity.HomeList;

import java.util.ArrayList;
import java.util.List;

public class NewData {

//给首页列表提供假数据
public static List<HomeList> getDate(){
    List<HomeList> list = new ArrayList<>();
    list.add(new HomeList(R.mipmap.ic_launcher,"dadwdada","dawdadadawd"));
    list.add(new HomeList(R.mipmap.ic_launcher,"dadwdada","dawdadadawd"));
    list.add(new HomeList(R.mipmap.ic_launcher,"dadwdada","dawdadadawd"));
    list.add(new HomeList(R.mipmap.ic_launcher,"dadwdada","dawdadadawd"));
    list.add(new HomeList(R.mipmap.ic_launcher,"dadwdada","dawdadadawd"));
    list.add(new HomeList(R.mipmap.ic_launcher,"dadwdada","dawdadadawd"));
    return list;
}


}
