package com.xr6software.theguardiannews.viewmodel.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xr6software.theguardiannews.database.model.NewsArticleDb
import com.xr6software.theguardiannews.repositories.NewsArticleRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsLocalViewModel @Inject constructor(
    private val newsArticleRepositoryImp: NewsArticleRepositoryImp,
) : ViewModel() {

    private var newsList = MutableLiveData<List<NewsArticleDb>>()

    fun getNewsList() = newsList

    fun loadSavedNews() {

        viewModelScope.launch {
            newsList.postValue(newsArticleRepositoryImp.getLocalNewsArticles())
        }

    }

}