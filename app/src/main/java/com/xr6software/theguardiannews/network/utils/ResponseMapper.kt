package com.xr6software.theguardiannews.network.utils

import com.google.gson.Gson
import com.xr6software.theguardiannews.model.NewsArticle
import com.xr6software.theguardiannews.model.NewsArticleInfo
import com.xr6software.theguardiannews.network.model.ApiResponse

/**
 *  This utility parses json arrays from apiService to News Items list and object.
 */
object ResponseMapper {

    fun parseResponseToNewsArticleInfoList(response : String): List<NewsArticleInfo> {
        return Gson().fromJson(response, ApiResponse::class.java).data.articles
    }

    fun parseResponseToNewsArticle (response: String) : NewsArticle {
        return Gson().fromJson(response, ApiResponse::class.java).data.newsContent.newsArticle
    }

}