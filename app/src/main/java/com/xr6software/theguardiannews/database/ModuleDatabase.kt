package com.xr6software.theguardiannews.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ModuleDatabase {

    @Provides
    fun provideNewsDetailItemDao(newsItemDatabase: NewsItemDatabase): NewsDetailItemDAO {
        return newsItemDatabase.newsItemDetailDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NewsItemDatabase {
        return Room.databaseBuilder(
            appContext,
            NewsItemDatabase::class.java,
            Companion.DATABASE_NAME
        ).build()
    }

    companion object {
        private const val DATABASE_NAME = "news_item_local_storage"
    }
}