package com.xr6software.theguardiannews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xr6software.theguardiannews.model.NewsDetailItem
import com.xr6software.theguardiannews.utils.DatabaseConverters

@Database(entities = [NewsDetailItem::class],version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class NewsItemDatabase : RoomDatabase() {

    abstract fun newsItemDetailDao() : NewsDetailItemDAO

}