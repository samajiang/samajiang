package com.example.android.entity

import com.google.gson.annotations.SerializedName
import java.util.Objects

class NewsDataresult(
    val error_code: Int,
    val reason: String,
    val result: Result
) {

}

class Result(
    val stat: String,
    val data: List<NewsItem>,
    val page: String,
    val pageSize: String
) {

}

class NewsItem(
    @SerializedName("uniquekey") val uniqueKey: String,
    @SerializedName("title") val title: String,
    @SerializedName("date") val publishDate: String, // 建议后续转成 DateTime 类型
    @SerializedName("category") val category: String,
    @SerializedName("author_name") val author: String,
    @SerializedName("url") val articleUrl: String,
    // 处理可能为空的图片字段（JSON中可能为""或null）
    @SerializedName("thumbnail_pic_s") val thumbnail: String? = null,
    @SerializedName("thumbnail_pic_s02") val thumbnail2: String? = null,
    @SerializedName("thumbnail_pic_s03") val thumbnail3: String? = null,
)