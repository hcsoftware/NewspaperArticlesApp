package com.xr6software.theguardiannews.network.model

import com.google.gson.annotations.SerializedName
import com.xr6software.theguardiannews.model.NewsArticle

data class NewsContent(
    val id : String,
    val sectionId: String,
    @SerializedName("fields")
    val newsArticle: NewsArticle
)