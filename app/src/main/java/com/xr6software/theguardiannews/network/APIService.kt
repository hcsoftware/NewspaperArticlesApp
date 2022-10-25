package com.xr6software.theguardiannews.network

import android.content.Context
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.xr6software.theguardiannews.model.NewsDetailItem
import com.xr6software.theguardiannews.model.Result
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

/**
 * This class interacts with the Guardian API server.
 */
@InstallIn(SingletonComponent::class)
@Module
class APIService @Inject constructor(@ApplicationContext val context: Context){

    private val apiKey : String = "test"
    //private val apiKey : String = "0ee4d7b1-9a1b-4d38-9c93-3301b8e2efbf"
    private val requestQueue = Volley.newRequestQueue(context)

    /**
     * gets the news list in array.
     * @param topic String search term
     * @param beginDate String format YYYY/MM/DD
     * @param endDate String format YYYY/MM/DD
     * @param callback Callback with onSuccess an onFailure Imp
    */
    fun loadNewsList(topic : String, beginDate: String, endDate: String, callback: Callback<List<Result>>) {

        val pageSize: String = "30" //Max Value = 50
        val requestUrl: String = "https://content.guardianapis.com/search?q=${topic}&format=json&show-fields=headline,thumbnail&page-size=${pageSize}&from-date=${beginDate.replace("/","-")}&to-date=${endDate.replace("/","-")}&api-key=${apiKey}"
        val jsonObjectRequest: JsonObjectRequest = JsonObjectRequest(
            requestUrl,
            { response ->
                callback.onSuccess(ResponseMapper().parseResponseToGsonArray(response.toString()))
            },
            {
                callback.onFailure(it)
            }

        )
        requestQueue.add(jsonObjectRequest)

    }

    /**
     * gets an unique new Item info from url
     * @param url String news item url address
     * @param callback Callback with onSuccess an onFailure Imp
     */
    fun loadNewsDetail(url: String, callback: Callback<NewsDetailItem>) {

        val requestUrl : String =  "${url}?show-fields=thumbnail,headline,firstPublicationDate,trailText,body&api-key=${apiKey}"
        val jsonObjectRequest: JsonObjectRequest = JsonObjectRequest(
            requestUrl,
            { response ->
                callback.onSuccess(ResponseMapper().parseResponseToResponseObject(response.toString()))
            },
            {
                callback.onFailure(it)
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

}