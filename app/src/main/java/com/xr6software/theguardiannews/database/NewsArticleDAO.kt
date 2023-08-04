package com.xr6software.theguardiannews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xr6software.theguardiannews.database.model.NewsArticleDb

/**
 * Data access object to interact with db.
 */
@Dao
interface NewsArticleDAO {

    @Query("SELECT * FROM NewsArticleDb")
    suspend fun getSavedNews(): List<NewsArticleDb>

    @Query("SELECT * FROM NewsArticleDb WHERE headline = :title")
    suspend fun getNewsByTitle(title: String): NewsArticleDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsItem (newsDetailItemDb: NewsArticleDb)

    @Query("DELETE FROM NewsArticleDb WHERE headline = :headline")
    suspend fun deleteNewsItem(headline: String) : Int

}