package com.example.android.Fragment

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.android.MainActivity
//import com.example.android.MainActivity
import com.example.android.R
import com.example.android.entity.UserInfo
import com.example.android.entity.userResult
import com.example.android.tool.BASEURL
import com.example.android.tool.SessionManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

open class UpdataUserDialog(context: Context) :DialogFragment(){
    private var oldpwd:EditText?=null
    private var seesion:SessionManager? = null
    private var prefuser:SharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
    private var password:EditText?=null
    private var age:EditText?=null
    private var sex:EditText?=null
    private var phone:EditText?=null
    private var title:TextView?=null
    private var btn:Button?=null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //        加载自定义布局
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.updata_userdialog, null)
        password = view.findViewById(R.id.etInputpassword);
        age = view.findViewById(R.id.etInputage);
        sex = view.findViewById(R.id.etInputsex);
        phone = view.findViewById(R.id.etInputuserphone);
        title = view.findViewById(R.id.tvTitle);
        btn = view.findViewById(R.id.btnSubmit);
        oldpwd = view.findViewById(R.id.etInputoldpassword)

        var username:String? = prefuser?.getString("username",null)
        if (username != null) {
            fetchuserbyname(username,object :fetchuserlistner{
                override fun fetchlistener(userInfo: UserInfo) {
                    btn?.setOnClickListener(){
                        var oldpassword = oldpwd?.text.toString()
                        var newpassword = password?.text.toString()
                        var newage = age?.text.toString()
                        var newsex = sex?.text.toString()
                        var newphone = phone?.text.toString()
                        if(oldpassword.equals(userInfo.password)){
                            var newadmin:UserInfo = UserInfo(userInfo.user_id,
                                userInfo.user_name,
                                newpassword,
                                newphone,
                                newage,
                                newsex,
                                userInfo.avatar_url,
                                userInfo.root)
                            updatauserinfo(newadmin)
                        }else{
                         Toast.makeText(context,"与原密码不一致请重新输入",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }






        return MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .create()
    }

//    通过用户名获取用户完整信息
fun fetchuserbyname(name:String,listener:fetchuserlistner){
    var client:OkHttpClient = OkHttpClient()
    var request:Request = Request.Builder()
        .url(BASEURL.getBase_URL()+"admin/fetchuserbyname/"+name)
        .build()
    client.newCall(request).enqueue(object :Callback{
        override fun onFailure(call: Call, e: IOException) {

        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            // 假设服务器返回的是JSON格式的列表
            // 创建泛型类型
            val responseType = object : TypeToken<userResult>() {}.type
            // 解析外层结构
            val apiResponse = Gson().fromJson<userResult>(body, responseType)
            var user:UserInfo = apiResponse.data
            listener.fetchlistener(user)
        }
    })
}

//    获取用户后的监听
    interface fetchuserlistner{
    fun fetchlistener(userInfo: UserInfo)
    }

//    更新用户信息
fun updatauserinfo(userInfo: UserInfo){
    var gson:Gson = Gson()
    var jsonuser = gson.toJson(userInfo)
    val mediaType: MediaType = "application/json".toMediaType()
    var requestbody:RequestBody = jsonuser.toRequestBody(mediaType)
    var client:OkHttpClient = OkHttpClient()
    var request:Request = Request.Builder()
        .url(BASEURL.getBase_URL()+"admin/updata")
        .post(requestbody)
        .build()
    client.newCall(request).enqueue(object :Callback{
        override fun onFailure(call: Call, e: IOException) {

        }

        override fun onResponse(call: Call, response: Response) {
            activity?.runOnUiThread(){
                Toast.makeText(context,"修改成功，请重新登录",Toast.LENGTH_SHORT).show()
                //             清空sharedprefernces的用户名和登录态
                seesion?.logout()
                val editor: SharedPreferences.Editor = prefuser.edit()
                editor.clear()
                editor.commit()
                (activity as MainActivity).restartappforuserupdata()
            }
        }
    })
}
}