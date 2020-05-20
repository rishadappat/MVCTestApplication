package com.appat.mvcapplication.API.Volley

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MVCApplication: Application() {
    private var requestQueue: RequestQueue? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(applicationContext)
        }
    }

    fun <T> addToRequestQueue(request: Request<T>, tag: String) {
        request.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(applicationContext)
        }
        requestQueue?.add(request)
    }

    fun cancelPendingRequests(tag: Any) {
        if (requestQueue != null) {
            requestQueue!!.cancelAll(tag)
        }
    }

    companion object {
        private val TAG = MVCApplication::class.java.simpleName
        var instance: MVCApplication? = null
    }
}