package com.example.android.Fragment

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.android.R
import com.example.android.entity.Order
import com.example.android.tool.BASEURL
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

open class UpdatabookDialog(context: Context,order: Order) : DialogFragment() {
    private var prefaddr:SharedPreferences = context.getSharedPreferences("addr",Context.MODE_PRIVATE)
    private var addrlist:Set<String> = setOf()
    private var sipnner:Spinner? =null
    private var ordername:EditText? = null
    private var ordertime:EditText? = null
    private var orderphone:EditText? = null
    private var ord:Order = order
    private var bookbtn:Button? =null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //        加载自定义布局
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialoglayout, null)
        ordername = view.findViewById(R.id.etInputname)
        ordertime = view.findViewById(R.id.etInputtime)
        orderphone = view.findViewById(R.id.etInputphone)
        sipnner = view.findViewById(R.id.spinner_hospital)
        bookbtn = view.findViewById(R.id.btnSubmit)

        ordername?.setText(ord.order_name)
        ordertime?.setText(ord.order_time)
        orderphone?.setText(ord.order_phone)
        //        从MainActivity中传入pref的医院地址列表
        addrlist = prefaddr.getStringSet("addrlist", emptySet())?: emptySet()
        val savedList = addrlist.toList()

        //        加载下拉菜单适配器
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, // 默认布局
            savedList
        ).apply {
            // 设置下拉菜单项的布局样式
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        // 将适配器设置给 Spinner
        sipnner?.adapter = adapter

        // 可选：设置默认选中项（例如第一个）
        if (savedList.isNotEmpty()) {
            sipnner?.setSelection(0)
        }

        //        点击提交按钮更新疫苗信息
        bookbtn?.setOnClickListener(){
            var addr = sipnner?.selectedItem as String
            var time = ordertime?.text.toString()
            var name = ordername?.text.toString()
            var phone = orderphone?.text.toString()
            var neworder:Order = Order(ord.order_id,name,time,phone,
                ord.user_name,ord.order_vaccine,addr)
            Log.d("updatabook", neworder.toString())
            var gson:Gson = Gson()
            var jsonneworder = gson.toJson(neworder)
            // 定义 JSON 的 MediaType
            val mediaType = "application/json".toMediaType()
            var requestbody = jsonneworder.toRequestBody(mediaType)

//            发送更新请求
            var client:OkHttpClient = OkHttpClient()
            var request:Request = Request.Builder()
                .url(BASEURL.getBase_URL()+"order/updatabyid")
                .post(requestbody)
                .build()
            client.newCall(request).enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    activity?.runOnUiThread {
                        Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show()
                        dialog?.dismiss()
                    }
                }
            })
        }

        //        创建对话框
        return MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .create()


    }
}