package com.xr6software.theguardiannews.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

fun EditText.validateUserInput() : Boolean {
    return (this.text.length < 15 ) && (this.text.isNotEmpty())
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Date.getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun String.datePeriodIsValid(endDate: String) : Boolean {
    val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy/MM/DD")

    return (dateFormat.parse(this)  < dateFormat.parse(endDate))
}

fun String.addDoubleQuotes(): String {
    return "\"".plus(this).plus("\"")
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}