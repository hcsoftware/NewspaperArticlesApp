package com.xr6software.theguardiannews.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6software.theguardiannews.database.NewsItemDatabase
import com.xr6software.theguardiannews.model.NewsDetailItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsLocalViewModel @Inject constructor(
        private val database: NewsItemDatabase
    ): ViewModel() {

    private var newsList = MutableLiveData<List<NewsDetailItem>>()

    fun getNewsList() = newsList

    fun getNewsListFromLocalDatabase(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            newsList.postValue(database.newsItemDetailDao().getSavedNews())
        }
    }

}