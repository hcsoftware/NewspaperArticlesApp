package com.xr6software.theguardiannews.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.xr6software.theguardiannews.model.NewsDetailItem
import com.xr6software.theguardiannews.model.Response
import com.xr6software.theguardiannews.model.Result

class APIService {

    private val apiKey : String = "0ee4d7b1-9a1b-4d38-9c93-3301b8e2efbf"

    fun loadNewsList(context: Context, topic : String, callback: Callback<List<Result>>) {

        val request: RequestQueue = Volley.newRequestQueue(context)
        val beginDate: String = "2022-01-01"
        val pageSize: String = "20" //Max Value = 50
        val requestUrl: String = "https://content.guardianapis.com/search?q=${topic}&format=json&show-fields=headline,thumbnail&page-size=${pageSize}&from-date=${beginDate}&api-key=${apiKey}"
        val stringRequest: StringRequest = StringRequest(
            Request.Method.GET,
            requestUrl,
            { response ->
                callback.onSucces(ResponseMapper().parseResponseToGsonArray(response))
            },
            {
                callback.onFailure(it)
            }

        )
        request.add(stringRequest)
    }

    fun loadNewsDetail(context: Context, url: String, callback: Callback<NewsDetailItem>) {
        val request : RequestQueue = Volley.newRequestQueue(context)
        val requestUrl : String =  "${url}?show-fields=thumbnail,headline,firstPublicationDate,trailText,body&api-key=${apiKey}"
        val stringRequest: StringRequest = StringRequest(
            Request.Method.GET,
            requestUrl,
            { response ->
                callback.onSucces(ResponseMapper().parseResponseToResponseObject(response))
            },
            {
                callback.onFailure(it)
            }
        )
        request.add(stringRequest)
    }

}