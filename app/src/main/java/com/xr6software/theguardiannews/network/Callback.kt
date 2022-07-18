package com.xr6software.theguardiannews.network


interface Callback<T> {

    fun onSucces(result: T?)

    fun onFailure(exception: Exception)

}