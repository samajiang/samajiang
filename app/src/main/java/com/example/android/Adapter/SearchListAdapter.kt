package com.example.android.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.entity.Vaccine
import okhttp3.OkHttpClient
import okhttp3.Request

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.MyHold>() {

    var searchlist: List<Vaccine> = listOf()
    var onclicklik:onclicklistenner ?=null

    @SuppressLint("NotifyDataSetChanged")
    fun searchsetdata(lists: List<Vaccine>) {
        this.searchlist = lists
        Log.d("searchlist", this.searchlist.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListAdapter.MyHold {
//        初始化数据列视图
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.vaccine_list_item, parent, false)
        return MyHold(view)
    }

    override fun onBindViewHolder(holder: SearchListAdapter.MyHold, position: Int) {
        var searchvaccine = searchlist[position]

        holder.searchvaccinename.text = searchvaccine.vaccine_name
        holder.searchdetail.text = searchvaccine.vaccine_datil

        holder.itemView.setOnClickListener {
            onclicklik?.searchclick(searchvaccine)
            true
        }

        var flag = searchvaccine.vaccine_pic
        if(flag != null){
            fetchvaccinepicbyflag(flag,holder)
        }else{
            Glide.with(holder.itemView.context)
                .load(R.mipmap.ic_launcher)
                .into(holder.searchpic)
        }
    }

    override fun getItemCount(): Int {
        return searchlist.size
    }

    class MyHold(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //        搜索到的疫苗图片
        var searchpic = itemView.findViewById<ImageView>(R.id.vaccinePic)

        //        搜索到的疫苗名字
        var searchvaccinename = itemView.findViewById<TextView>(R.id.vaccinename)

        //        搜索到的疫苗说明
        var searchdetail = itemView.findViewById<TextView>(R.id.vaccineDetail)

    }

//    通过疫苗图片的flag去获取图片链接的方法
fun fetchvaccinepicbyflag(flag:String,holder: SearchListAdapter.MyHold){
    val url:String = "http://192.168.1.103:8080/api/file/"
    val fullurl:String = url+flag
    Log.d("fetchvaccinepicbyflag", fullurl)
    Glide.with(holder.itemView.context)
        .load(fullurl)
        .into(holder.searchpic)
}

//    点击事件监听
interface onclicklistenner{
    fun searchclick(vaccine:Vaccine)
}
//    设置点击事件监听的方法
fun setonclicklistenner(listenner:onclicklistenner){
    this.onclicklik = listenner
}

}