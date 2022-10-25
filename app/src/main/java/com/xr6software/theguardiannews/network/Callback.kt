package com.xr6software.theguardiannews.network


interface Callback<T> {

    fun onSuccess(result: T?)

    fun onFailure(exception: Exception)

}