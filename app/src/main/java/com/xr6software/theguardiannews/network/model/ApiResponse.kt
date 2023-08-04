package com.xr6software.theguardiannews.network.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("response")
    val data: ApiData
)


