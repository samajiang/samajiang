package com.example.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.android.Adapter.SearchListAdapter
import com.example.android.entity.SearchRespone
import com.example.android.entity.Vaccine
import com.example.android.tool.BASEURL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Type

class SearchActivity : AppCompatActivity() {
//    private var BASE_URL = "http://192.168.1.102:8080/api/search/searchvaccine/"
    private var searchtext: EditText? = null
    private var searchrecycelview: RecyclerView? = null
    private var searchadapter: SearchListAdapter = SearchListAdapter()
    private var search:String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
//        初始化组件
        searchtext = findViewById(R.id.search_text)
        searchrecycelview = findViewById(R.id.search_resyclerview)
        search = intent.getStringExtra("vaccinename")?: ""
        Log.d("search", intent.getStringExtra("vaccinename")?: "")
        searchtext?.setText(search)
        searchvaccine()
        jumpbylistenner()
    }

    //    通过模糊查询获取疫苗的方法
    fun searchvaccine() {
        var uul = intent.getStringExtra("vaccinename")
            var client: OkHttpClient = OkHttpClient()
            var request: Request = Request.Builder()
                .url(BASEURL.getBase_URL()+"search/searchvaccine/"+uul)
                .build()
        Log.d("BASE_URL+searchtext", BASEURL.getBase_URL()+"search/searchvaccine/"+uul)
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(this@SearchActivity, "获取数据失败", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    var res: String? = response.body?.string()
                    Log.d("res", res.toString())
                    var listtype: Type = object : TypeToken<SearchRespone>() {}.type
                    var respone: SearchRespone = Gson().fromJson(res, listtype)
                    var lists: List<Vaccine>? = respone.data
                    Log.d("searchvaccine", lists.toString())
                    runOnUiThread {
                        setRandA(lists)
                    }

                }
            })
    }

    //    设置recycleview和adapter
    fun setRandA(lists: List<Vaccine>?) {
        Log.d("setRandA", lists.toString())
        if (lists != null) {
            searchadapter.searchsetdata(lists)
            searchrecycelview?.adapter = searchadapter
        }

    }

//    设置监听器并在里边实现页面跳转
fun jumpbylistenner(){
    searchadapter.setonclicklistenner(object : SearchListAdapter.onclicklistenner{
        override fun searchclick(vaccine: Vaccine) {


            // 跳转到详情页
            val intent = Intent(this@SearchActivity, VaccineDetailActivity::class.java)
            intent.putExtra("vaccine_id", vaccine.vaccine_id)
            startActivity(intent)
        }

    })
}
}