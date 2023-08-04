package com.xr6software.theguardiannews.network.model

import com.google.gson.annotations.SerializedName
import com.xr6software.theguardiannews.model.NewsArticleInfo

data class ApiData(
    val status: String,
    val userTier: String,
    val total: Int,
    val startIndex: Int,
    val pageSize: Int,
    val currentPage: Int,
    val pages: Int,
    val orderBy: String,
    @SerializedName("results") val articles: List<NewsArticleInfo>,
    @SerializedName("content") val newsContent : NewsContent
)