package com.xr6software.theguardiannews.database

import androidx.room.*
import com.xr6software.theguardiannews.model.NewsDetailItem

@Dao
interface NewsDetailItemDAO {

    @Query("SELECT * FROM NewsDetailItem")
    fun getSavedNews(): List<NewsDetailItem>

    @Query("SELECT * FROM NewsDetailItem WHERE headline = :title")
    fun getNewsByTitle(title: String): NewsDetailItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsItem (newsDetailItem: NewsDetailItem)

    @Query("DELETE FROM NewsDetailItem WHERE headline = :headline")
    fun deleteNewsItem(headline: String)

}