package com.xr6software.theguardiannews.model

import com.xr6software.theguardiannews.database.model.NewsArticleDb
import java.io.Serializable
import java.util.*

data class NewsArticle (
    val headline: String,
    val thumbnail: String?,
    val firstPublicationDate: Date?,
    val trailText: String?,
    val body: String?
    ) : Serializable

fun NewsArticle.toNewsArticleDb() =
    NewsArticleDb(
        this.headline,
        this.thumbnail ?: "https://i.guim.co.uk/img/media/6caa3873f43309c684c2142636e3f2383a709187/12_0_1393_836/500.jpg?quality=85&auto=format&fit=max&s=6bdb3efd1f1c94d53c8939cf614e7fdb",
        this.firstPublicationDate ?: Date(),
        this.trailText ?: "",
        this.body ?: ""
    )



