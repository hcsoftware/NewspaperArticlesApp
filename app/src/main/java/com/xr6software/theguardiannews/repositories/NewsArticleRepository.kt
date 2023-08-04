package com.xr6software.theguardiannews.repositories

import com.xr6software.theguardiannews.database.model.NewsArticleDb
import com.xr6software.theguardiannews.model.NewsArticle
import com.xr6software.theguardiannews.model.NewsArticleInfo
import com.xr6software.theguardiannews.repositories.model.RepositoryStatus

interface NewsArticleRepository {

    //TODO Result should be rename, it's a news article as it comes from the API
    //APIRest methods
    suspend fun searchNewsArticles(topic: String, beginDate: String, endDate: String) : RepositoryStatus<List<NewsArticleInfo>>
    suspend fun loadNewsArticle(newsArticleUrl: String) : RepositoryStatus<NewsArticle>

    //Local DB Room methods
    suspend fun getLocalNewsByTitle(title: String) : Boolean
    suspend fun getLocalItemTitle(title: String): NewsArticle
    suspend fun getLocalNewsArticles() : List<NewsArticleDb>
    suspend fun saveNewsArticleLocally(newsArticleDb: NewsArticleDb)
    suspend fun deleteNewsArticleLocally(newsArticleDb: NewsArticleDb)

}