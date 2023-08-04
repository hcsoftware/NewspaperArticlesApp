package com.xr6software.theguardiannews.repositories

import com.xr6software.theguardiannews.database.NewsArticleDatabase
import com.xr6software.theguardiannews.database.model.NewsArticleDb
import com.xr6software.theguardiannews.database.model.toNewsArticle
import com.xr6software.theguardiannews.model.NewsArticle
import com.xr6software.theguardiannews.model.NewsArticleInfo
import com.xr6software.theguardiannews.network.APIService
import com.xr6software.theguardiannews.network.Callback
import com.xr6software.theguardiannews.repositories.model.RepositoryStatus
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@InstallIn(SingletonComponent::class)
@Module
class NewsArticleRepositoryImp @Inject constructor(
    private val apiService: APIService,
    private val database: NewsArticleDatabase
): NewsArticleRepository {

    /**
     *  Loads news from Api service given a search term and dates.
     *  @param topic String
     *  @param beginDate String
     *  @param endDate String
     */
    override suspend fun searchNewsArticles(
        topic: String,
        beginDate: String,
        endDate: String
    ): RepositoryStatus<List<NewsArticleInfo>> = suspendCoroutine { cont ->

        apiService.loadNewsList(topic,beginDate, endDate, object: Callback<List<NewsArticleInfo>>{
            override fun onSuccess(result: List<NewsArticleInfo>) {
                cont.resume(RepositoryStatus.Success<List<NewsArticleInfo>>(result))
            }
            override fun onFailure(exception: Exception) {
                cont.resume(RepositoryStatus.Failed(exception.message.toString()))
            }
        })

    }

    /**
     *  Load news Article from Api service
     *  @param newsArticleUrl String
     */
    override suspend fun loadNewsArticle(
        newsArticleUrl: String
    ): RepositoryStatus<NewsArticle> = suspendCoroutine { cont ->
        apiService.loadNewsDetail(newsArticleUrl, object: Callback<NewsArticle>{
            override fun onSuccess(result: NewsArticle) {
                cont.resume(RepositoryStatus.Success(result))
            }
            override fun onFailure(exception: Exception) {
                cont.resume(RepositoryStatus.Failed(exception.message.toString()))
            }
        })
    }

    /**
     * Returns an single item from db given a title string
     */
    override suspend fun getLocalItemTitle(title: String): NewsArticle {
        return database.newsArticleDao().getNewsByTitle(title)!!.toNewsArticle()
    }

    /**
     * returns true if an item is on db, given a title
     */
    override suspend fun getLocalNewsByTitle(title: String): Boolean {
        return (database.newsArticleDao().getNewsByTitle(title) != null)
    }

    /**
     * Provides all news articles items stored locally
     */
    override suspend fun getLocalNewsArticles(): List<NewsArticleDb> {
        return database.newsArticleDao().getSavedNews()
    }
    /**
     * Stores news article locally in db.
     * @param newsArticleDb newsItem to save in db.
     */
    override suspend fun saveNewsArticleLocally(newsArticleDb: NewsArticleDb) {
        return database.newsArticleDao().insertNewsItem(newsArticleDb)
    }

    /**
     * Deletes news article locally
     * @param newsArticleDb news Item to delete. Uses headline to delete from db.
     */
    override suspend fun deleteNewsArticleLocally(newsArticleDb: NewsArticleDb) {
        database.newsArticleDao().deleteNewsItem(newsArticleDb.headline)
    }

}