package com.xr6software.theguardiannews.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xr6software.theguardiannews.database.model.NewsArticleDb
import com.xr6software.theguardiannews.utils.DatabaseConverters

/**
 * @author Hernán Carrera
 * @version 1.0
 * Room Database class to store news items locally.
 */
@Database(entities = [NewsArticleDb::class],version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class NewsArticleDatabase : RoomDatabase() {

    abstract fun newsArticleDao() : NewsArticleDAO

}