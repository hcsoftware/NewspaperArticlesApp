package com.xr6software.theguardiannews.utils

import androidx.room.TypeConverter

/**
 *  Converts date format for db.
 */
class DatabaseConverters {

    @TypeConverter
    fun fromTimestamp( value: Long?) :
            java.util.Date {
        return java.util.Date(value ?: 0)
    }
    @TypeConverter
    fun dateToTimestamp(date :java.util.Date?)
            :Long {
        return date?.time ?: 0
    }

}