package com.appat.mvcapplication.Utilities

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.appat.mvcapplication.API.Enums.BaseUrl
import com.appat.mvcapplication.API.Enums.Environment
import com.appat.mvcapplication.API.Volley.MVCApplication
import com.appat.mvcapplication.R

object Utility {
    private var loaderDialog: AlertDialog? = null

    var environment: Environment = Environment.Test

    fun getContext(): Context
    {
        return MVCApplication.instance!!.applicationContext
    }

    fun getBaseURL() : String {
        return when (environment) {
            Environment.Production -> BaseUrl.ProductionBaseUrl.baseUrl
            Environment.Test -> BaseUrl.TestBaseUrl.baseUrl
            else -> ""
        }
    }

    fun isNetworkAvailable(): Boolean {
        val cm = getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                val ni = cm.activeNetworkInfo
                if (ni != null) {
                    return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE || ni.type == ConnectivityManager.TYPE_BLUETOOTH)
                }
            } else {
                val n = cm.activeNetwork
                if (n != null) {
                    val nc = cm.getNetworkCapabilities(n)
                    if(nc != null) {
                        return nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                nc.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
                    }
                }
            }
        }
        return false
    }

    fun showLoaderDialog(context: Context?, message: String)
    {
        if(context == null)
        {
            return
        }
        if(context is AppCompatActivity) {
            hideKeyboard(context)
        }
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.loader_dialog, null, false)
        dialogBuilder.setView(dialogView)
        val messageView = dialogView.findViewById<TextView>(R.id.message)
        messageView.text = message
        val dialog = dialogBuilder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if(!(context as AppCompatActivity).isFinishing)
        {
            dialog.show()
            loaderDialog = dialog
        }
    }

    fun hideLoaderDialog()
    {
        if(loaderDialog != null)
        {
            if (loaderDialog!!.isShowing)
            {
                try{
                    loaderDialog!!.dismiss()
                }
                catch (e: Exception)
                {

                }
            }
        }
    }

    fun hideKeyboard(context: Activity) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = context.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(getContext())
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}