package com.example.android.entity

import com.google.gson.annotations.SerializedName
import java.util.Objects

class Resultdata(
    val code:Int,
    val msg:String? = null,
    val data:Any
)

class ResultD(
    val code:Int,
    val msg:String? = null,
    val data:Vaccine
)

class userResult(
    val code:Int,
    val msg:String? = null,
    val data:UserInfo
)

