package com.xr6software.theguardiannews.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xr6software.theguardiannews.model.NewsArticle
import java.util.*

@Entity
data class NewsArticleDb(
    @ColumnInfo(name = "headline") val headline: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String = "https://clipground.com/images/guardian-logo-png-2.png",
    @ColumnInfo(name = "firstPublicationDate") val firstPublicationDate: Date,
    @ColumnInfo(name = "trailText") val trailText: String,
    @ColumnInfo(name = "body") val body: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

fun NewsArticleDb.toNewsArticle() =
    NewsArticle(
        this.headline,
        this.thumbnail,
        this.firstPublicationDate,
        this.trailText,
        this.body
    )
