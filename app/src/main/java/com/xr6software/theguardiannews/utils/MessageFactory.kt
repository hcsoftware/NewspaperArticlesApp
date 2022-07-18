package com.xr6software.theguardiannews.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MessageFactory {

    companion object {

        fun showToast(context: Context, msg: Int, gravity : Int) {

            val toast : Toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            toast.setGravity(gravity, 0, 0)
            toast.show()
        }

        fun showSnackBar(parentView: View, msg: Int, showOk : Boolean, length : Int) {
            var snackbar : Snackbar = Snackbar.make(parentView, msg, length)
            snackbar.setBackgroundTint(Color.parseColor("#052962"))
            if (showOk) {  snackbar.setAction("OK"){ } }
            snackbar.show()
        }

    }

}