package com.xr6software.theguardiannews.viewmodel.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xr6software.theguardiannews.model.NewsArticleInfo
import com.xr6software.theguardiannews.repositories.NewsArticleRepositoryImp
import com.xr6software.theguardiannews.repositories.model.RepositoryStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSearchViewModel @Inject constructor(
    private val newsArticleRepositoryImp: NewsArticleRepositoryImp
    ): ViewModel() {

    private val uiSearchFragmentState = MutableLiveData<UiSearchFragmentState>()
    fun getUiState() = uiSearchFragmentState

    /**
     *  Search for news articles in repository.
     *  @param topic String
     *  @param beginDate String
     *  @param endDate String
     */
    fun searchNewsArticles(topic : String,beginDate : String, endDate : String) {

        uiSearchFragmentState.value =UiSearchFragmentState.Loading

        viewModelScope.launch {
            val searchResult = newsArticleRepositoryImp.searchNewsArticles(
                topic, beginDate, endDate
            )
            when (searchResult) {
                is RepositoryStatus.Success<List<NewsArticleInfo>> -> {
                    if(searchResult.data.isEmpty()) {
                        uiSearchFragmentState.value = UiSearchFragmentState.NoResults
                    }
                    else {
                        uiSearchFragmentState.value =
                            UiSearchFragmentState.Articles(searchResult.data!!)
                    }
                }
                is RepositoryStatus.Failed -> {
                    uiSearchFragmentState.postValue(UiSearchFragmentState.Error(searchResult.message!!))
                }
            }
        }

    }

    sealed class UiSearchFragmentState {
        object Loading : UiSearchFragmentState()
        object NoResults: UiSearchFragmentState()
        data class Error(val error: String) : UiSearchFragmentState()
        data class Articles(val articlesList: List<NewsArticleInfo>) : UiSearchFragmentState()
    }

}