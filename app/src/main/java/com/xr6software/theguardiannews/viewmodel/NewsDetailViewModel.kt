package com.xr6software.theguardiannews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6software.theguardiannews.database.NewsItemDatabase
import com.xr6software.theguardiannews.model.NewsDetailItem
import com.xr6software.theguardiannews.network.APIService
import com.xr6software.theguardiannews.network.Callback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val database: NewsItemDatabase,
    private val apiService: APIService
): ViewModel() {

    private var newsDetailItem = MutableLiveData<NewsDetailItem>()
    private var isLoading : MutableLiveData<Boolean> = MutableLiveData(false)
    private var errorLoading : MutableLiveData<Boolean> = MutableLiveData(false)
    private var itemOnLocalDb : MutableLiveData<Boolean> = MutableLiveData(false)

    fun getNewsDetailItem() = newsDetailItem
    fun getIsLoading() = isLoading
    fun getErrorLoading() = errorLoading
    fun getItemOnLocalDb() = itemOnLocalDb

    fun getNewsDetailFromAPIService(itemUrl : String) {

        errorLoading.value = false
        isLoading.value = true

        apiService.loadNewsDetail(itemUrl, object : Callback<NewsDetailItem> {
            override fun onSuccess(result: NewsDetailItem?) {
                newsDetailItem.postValue(result!!)
                isLoading.postValue(false)
            }

            override fun onFailure(exception: Exception) {
                isLoading.postValue(false)
                errorLoading.postValue(true)
            }

        })

    }

    fun getNewsSavedOnDbStatus(newsTitle: String) {

        CoroutineScope(IO).launch {
            itemOnLocalDb.postValue(database.newsItemDetailDao().getNewsByTitle(newsTitle) != null)
        }

    }

    fun updateLocalDbStatus(newsDetailItem: NewsDetailItem) {
        if (itemOnLocalDb.value == false) {saveNewsItemOnLocalDb(newsDetailItem)}
        else {deleteNewsItemOnLocalDb(newsDetailItem)}
    }

    fun saveNewsItemOnLocalDb(newsDetailItem: NewsDetailItem) {
        CoroutineScope(IO).launch {
            database.newsItemDetailDao().insertNewsItem(newsDetailItem)
            itemOnLocalDb.postValue(true)
        }
    }

    fun deleteNewsItemOnLocalDb(newsDetailItem: NewsDetailItem) {
        CoroutineScope(IO).launch {
            database.newsItemDetailDao().deleteNewsItem(newsDetailItem.headline)
            itemOnLocalDb.postValue(false)
        }
    }

}