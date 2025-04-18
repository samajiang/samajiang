package com.example.android.entity

import com.google.gson.Gson

class Order(
    val order_id: Int? = null,
    val order_name: String,
    val order_time: String,
    val order_phone: String,
    val user_name: String? = null,
    val order_vaccine:String,
    val order_addr:String

)
