package com.xr6software.theguardiannews.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class NewsArticleInfo(
    val id: String,
    val type: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: Date?,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    @SerializedName("fields") val newsArticle: NewsArticle,
    val isHosted: Boolean,
    val pillarId: String,
    val pillarName: String
) : Serializable

fun NewsArticleInfo.toNewsArticle() =
    NewsArticle(
        this.newsArticle.headline,
        this.newsArticle.thumbnail ?: "https://i.guim.co.uk/img/media/6caa3873f43309c684c2142636e3f2383a709187/12_0_1393_836/500.jpg?quality=85&auto=format&fit=max&s=6bdb3efd1f1c94d53c8939cf614e7fdb",
        this.webPublicationDate,
        this.newsArticle.trailText,
        this.newsArticle.body
    )