package com.xr6software.theguardiannews.viewmodel.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xr6software.theguardiannews.database.model.NewsArticleDb
import com.xr6software.theguardiannews.model.NewsArticle
import com.xr6software.theguardiannews.repositories.NewsArticleRepositoryImp
import com.xr6software.theguardiannews.repositories.model.RepositoryStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val newsArticleRepositoryImp: NewsArticleRepositoryImp
): ViewModel() {

    private var newsDetailUiState = MutableLiveData<NewsDetailUiState>()
    fun getUiState() = newsDetailUiState

    private var itemIsLocal : MutableLiveData<Boolean> = MutableLiveData(false)
    fun getIsLocally() = itemIsLocal

    fun getNewsArticle(itemUrl : String) {

        newsDetailUiState.value = NewsDetailUiState.Loading
        viewModelScope.launch {
            when (val result = newsArticleRepositoryImp.loadNewsArticle(itemUrl)) {
                is RepositoryStatus.Success<NewsArticle> -> {
                    newsDetailUiState.value = NewsDetailUiState.NewsDetail(result.data)
                }
                is RepositoryStatus.Failed -> {
                    newsDetailUiState.value = NewsDetailUiState.Error(result.message)
                }
            }
        }

    }

    fun getLocalNewsArticle(newsTitle : String) {

        newsDetailUiState.value = NewsDetailUiState.Loading
        viewModelScope.launch {
            newsDetailUiState.value = NewsDetailUiState.NewsDetail(newsArticleRepositoryImp.getLocalItemTitle(newsTitle))
        }

    }

    fun getIsLocallyStatus(title: String) {
        CoroutineScope(IO).launch {
            itemIsLocal.postValue(newsArticleRepositoryImp.getLocalNewsByTitle(title))
        }
    }

    fun updateLocalDbStatus(newsDetailItemDb: NewsArticleDb) {
        if (itemIsLocal.value == false) {saveNewsItemOnLocalDb(newsDetailItemDb)}
        else {deleteNewsItemOnLocalDb(newsDetailItemDb)}
    }

    private fun saveNewsItemOnLocalDb(newsDetailItemDb: NewsArticleDb) {
        CoroutineScope(IO).launch {
            newsArticleRepositoryImp.saveNewsArticleLocally(newsDetailItemDb)
            itemIsLocal.postValue(true)
        }
    }

    private fun deleteNewsItemOnLocalDb(newsDetailItemDb: NewsArticleDb) {
        CoroutineScope(IO).launch {
            newsArticleRepositoryImp.deleteNewsArticleLocally(newsDetailItemDb)
            itemIsLocal.postValue(false)
        }
    }

    sealed class NewsDetailUiState {
        object  Loading : NewsDetailUiState()
        data class Error(val error: String) : NewsDetailUiState()
        data class NewsDetail(val newsDetail: NewsArticle) : NewsDetailUiState()
    }

}