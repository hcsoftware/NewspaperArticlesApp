package com.xr6software.theguardiannews.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

/**
 * Data classes used for api calls and news.
 */
data class Root(
    val response: Response
)

data class Response(
    val status: String,
    val userTier: String,
    val total: Int,
    val startIndex: Int,
    val pageSize: Int,
    val currentPage: Int,
    val pages: Int,
    val orderBy: String,
    val results: ArrayList<Result>,
    val content: Content
)

data class Result(
    val id: String,
    val type: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: Date,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val fields: Fields,
    val isHosted: Boolean,
    val pillarId: String,
    val pillarName: String
)

data class Content(
    val fields: Fields
)

data class Fields(
    val headline: String = "",
    val thumbnail: String = "",
    val firstPublicationDate: Date,
    val trailText: String = "",
    val body: String = ""
)

data class NewsListItem(
    val webPublicationDate: Date,
    val apiUrl: String,
    val headline: String,
    val thumbnail: String
) : Serializable

fun Result.toNewsListItem() = NewsListItem(

    webPublicationDate,
    apiUrl,
    fields.headline,
    fields.thumbnail ?: "https://clipground.com/images/guardian-logo-png-2.png"

)


@Entity
data class NewsDetailItem(
    @ColumnInfo(name = "headline") val headline: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "firstPublicationDate") val firstPublicationDate: Date,
    @ColumnInfo(name = "trailText") val trailText: String,
    @ColumnInfo(name = "body") val body: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

data class NewsDetailItemLocal(
    val headline: String,
    val thumbnail: String,
    val firstPublicationDate: Date,
    val trailText: String,
    val body: String
) : Serializable

fun NewsDetailItem.toNewsDetailItemLocal() = NewsDetailItemLocal(
    headline, thumbnail, firstPublicationDate, trailText, body
)


fun Response.toNewsDetailItem() = NewsDetailItem(
    content.fields.headline,
    content.fields.thumbnail,
    content.fields.firstPublicationDate,
    content.fields.trailText,
    content.fields.body
)