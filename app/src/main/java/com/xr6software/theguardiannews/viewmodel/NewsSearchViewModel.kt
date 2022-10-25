package com.xr6software.theguardiannews.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6software.theguardiannews.model.NewsListItem
import com.xr6software.theguardiannews.model.Result
import com.xr6software.theguardiannews.model.toNewsListItem
import com.xr6software.theguardiannews.network.APIService
import com.xr6software.theguardiannews.network.Callback
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsSearchViewModel @Inject constructor(private val apiService: APIService): ViewModel() {

    private var newsList = MutableLiveData<List<NewsListItem>>()
    private var isLoading : MutableLiveData<Boolean> = MutableLiveData(false)
    private var noResults : MutableLiveData<Boolean> = MutableLiveData(false)
    private var errorLoading : MutableLiveData<Boolean> = MutableLiveData(false)

    fun getNewsList() : MutableLiveData<List<NewsListItem>> = newsList
    fun getIsLoading() : MutableLiveData<Boolean> = isLoading
    fun getNoResults() : MutableLiveData<Boolean> = noResults
    fun getErrorLoading() : MutableLiveData<Boolean> = errorLoading

    fun getNewsListFromAPIService(topic : String,beginDate : String, endDate : String) {

        noResults.value = false
        isLoading.value = true
        apiService.loadNewsList(topic, beginDate, endDate, object : Callback<List<Result>>{
            override fun onSuccess(result: List<Result>?) {

                if (!result!!.isEmpty()) {
                    var newsListCast : MutableList<NewsListItem> = mutableListOf()
                    result?.forEach {
                        newsListCast.add(it.toNewsListItem())
                        newsList.postValue(newsListCast)
                    }
                }
                else {
                    noResults.postValue(true)
                }
                isLoading.postValue(false)
            }

            override fun onFailure(exception: Exception) {
                isLoading.postValue(false)
                errorLoading.postValue(true)
            }

        })

    }

}