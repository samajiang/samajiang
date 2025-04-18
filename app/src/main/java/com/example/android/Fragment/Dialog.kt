package com.example.android.Fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.android.R
import com.example.android.VaccineDetailActivity
import com.example.android.entity.Order
import com.example.android.entity.ResultD
import com.example.android.entity.Resultdata
import com.example.android.entity.Vaccine
import com.example.android.entity.VaccineRespone
import com.example.android.tool.BASEURL
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

open class Dialog(content: Context, vaccinename: String,vaccineid:Int) : DialogFragment() {
    var addperf = content.getSharedPreferences("addr",Context.MODE_PRIVATE)
    var addrlist:Set<String> = setOf()
    var perf = content.getSharedPreferences("user", Context.MODE_PRIVATE)
//    val BASE_URL = "http://192.168.1.101:8080/api/"
    val activity = VaccineDetailActivity()
    val v = vaccinename
    val a = BookVaccineFragment()
    var vvc = vaccineid
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {



//        从MainActivity中传入pref的医院地址列表
        addrlist = addperf.getStringSet("addrlist", emptySet())?: emptySet()
        val savedList = addrlist.toList()
        Log.d("addrlist", savedList.toString())

//        加载自定义布局
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialoglayout, null)
//        加载下拉菜单适配器
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, // 默认布局
            savedList
        ).apply {
            // 设置下拉菜单项的布局样式
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

//        绑定布局里的组件
        val spinneryiyuan = view.findViewById<Spinner>(R.id.spinner_hospital)
        val title = view.findViewById<TextView>(R.id.tvTitle)
        val inputname = view.findViewById<EditText>(R.id.etInputname)
        val inputtime = view.findViewById<EditText>(R.id.etInputtime)
        val inputphone = view.findViewById<EditText>(R.id.etInputphone)
        val subbtn = view.findViewById<Button>(R.id.btnSubmit)

// 将适配器设置给 Spinner
        spinneryiyuan.adapter = adapter

        // 可选：设置默认选中项（例如第一个）
        if (savedList.isNotEmpty()) {
            spinneryiyuan.setSelection(0)
        }

        //        调用获取选择疫苗的方法
        searchvaccine(vvc,object :returncount{
            override fun vcount(vc: Vaccine) {
                //        提交按钮提交预定信息
                subbtn.setOnClickListener() {
                    var add = spinneryiyuan.selectedItem as String
                    var n = inputname.text.toString()
                    var t = inputtime.text.toString()
                    var p = inputphone.text.toString()
                    var u = perf?.getString("username", null)
                    Log.d("usernameorder", u.toString())
                    Log.d("vn", v)
                    val order = Order(order_name = n, order_time = t, order_phone = p, user_name = u, order_vaccine = v,order_addr = add)
                    val gson = Gson()
                    val jsonorder = gson.toJson(order)
                    Log.d("jsonorder", jsonorder)

                    // 定义 JSON 的 MediaType
                    val mediaType = "application/json".toMediaType()

                    val requestBody = jsonorder.toRequestBody(mediaType)


//            开始发送预定网络请求
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url(BASEURL.getBase_URL() + "order/ordering")
                        .post(requestBody)
                        .build()
                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            activity.runOnUiThread {
                                Toast.makeText(context, "预定失败请重试", Toast.LENGTH_SHORT).show()
                            }

                        }

                        override fun onResponse(call: Call, response: Response) {
                            //                疫苗减一的方法
                            jianvaccine(vc)
                            val res = response.body?.string()
                            val body = JSONObject(res)
                            val result = Resultdata(body.getInt("code"), body.getString("msg"), body.get("data"))
                            activity.runOnUiThread {
                                if(result.code ==0){
                                    Toast.makeText(context, result.data.toString(), Toast.LENGTH_SHORT).show()
                                    dialog?.dismiss()
                                }
                                if(result.code == -1){
                                    Toast.makeText(context, result.msg.toString(), Toast.LENGTH_SHORT).show()
                                }

                            }


                        }
                    })
                }
            }
        })



//        创建对话框
        return MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .create()


    }
//    疫苗减一方法
fun jianvaccine(count:Vaccine){

    val clinet:OkHttpClient = OkHttpClient()
    var oldvc:Vaccine = count
    var getcount:Int = oldvc.vaccine_count
    var setcount: Int = getcount-1
    oldvc.vaccine_count = setcount
    var requestbody:RequestBody = Gson().toJson(oldvc).toRequestBody("application/json; charset=utf-8".toMediaType())
    val request:Request = Request.Builder()
        .post(requestbody)
        .url(BASEURL.getBase_URL()+"vaccine/addvaccine")
        .build()
    clinet.newCall(request).enqueue(object :Callback{
        override fun onFailure(call: Call, e: IOException) {

        }

        override fun onResponse(call: Call, response: Response) {

        }
    })

}
//    查疫苗信息方法
fun searchvaccine(vaccineID: Int,returncount: returncount){
    var client:OkHttpClient = OkHttpClient()
    var request:Request = Request.Builder()
        .url(BASEURL.getBase_URL()+"vaccine/searchbyid/"+vaccineID.toString())
        .build()
    client.newCall(request).enqueue(object :Callback{
        override fun onFailure(call: Call, e: IOException) {

        }

        override fun onResponse(call: Call, response: Response) {
            var body:String? = response.body?.string()
            // 假设服务器返回的是JSON格式的列表
            // 创建泛型类型
            val responseType = object : TypeToken<ResultD>() {}.type
            // 解析外层结构
            val apiResponse = Gson().fromJson<ResultD>(body, responseType)
            var va:Vaccine = apiResponse.data
            returncount.vcount(va)
        }
    })
}

//    拿到疫苗数量后的回调方法
fun interface returncount{
    fun vcount(vc:Vaccine)
}
}