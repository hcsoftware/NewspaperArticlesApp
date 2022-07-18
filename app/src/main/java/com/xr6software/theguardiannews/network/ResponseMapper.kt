package com.xr6software.theguardiannews.network

import com.google.gson.Gson
import com.xr6software.theguardiannews.model.*

class ResponseMapper {

    fun parseResponseToGsonArray(response: String) : List<Result>{

        return Gson().fromJson(response, Root::class.java).response.results

    }

    fun parseResponseToResponseObject(response: String) : NewsDetailItem {
        return Gson().fromJson(response, Root::class.java).response.toNewsDetailItem()
    }

}