package com.xr6software.theguardiannews.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xr6software.theguardiannews.model.NewsDetailItem
import com.xr6software.theguardiannews.utils.DatabaseConverters

/**
 * @author Hernán Carrera
 * @version 1.0
 * Room Database class to store news items locally.
 */
@Database(entities = [NewsDetailItem::class],version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class NewsItemDatabase : RoomDatabase() {

    abstract fun newsItemDetailDao() : NewsDetailItemDAO

}