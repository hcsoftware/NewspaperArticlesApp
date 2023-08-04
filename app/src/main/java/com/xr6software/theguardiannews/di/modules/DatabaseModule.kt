package com.xr6software.theguardiannews.di.modules

import android.content.Context
import androidx.room.Room
import com.xr6software.theguardiannews.database.NewsArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides connection for db in app.
 */
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppNewsDatabase(@ApplicationContext appContext: Context): NewsArticleDatabase {
        return Room.databaseBuilder(
            appContext,
            NewsArticleDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    companion object {
        private const val DATABASE_NAME = "news_item_local_storage"
    }

}