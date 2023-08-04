package com.xr6software.theguardiannews.view.adapters

interface AdapterClickListener<T> {

    fun onClick(item: T, position: Int)

}