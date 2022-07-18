package com.xr6software.theguardiannews.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

fun EditText.validateUserInput() : Boolean {
    return (this.text.length < 15 ) && (this.text.isNotEmpty())
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun TextView.fixTextSimbolsAndLoad(text: String) {
        this.text = text.replace("â\u0080\u0098", "'").replace("â\u0080\u0099","'").replace("â\u0080\u0093","-").replace("Â£","£").replace("Ã¼n","ü").replace("â\u0080","€")

}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}