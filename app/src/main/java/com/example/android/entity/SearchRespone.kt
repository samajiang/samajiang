package com.example.android.entity

data class SearchRespone(
    var code: String? = null,
    var msg: String ?= null,
    var data: List<Vaccine>? = null
)