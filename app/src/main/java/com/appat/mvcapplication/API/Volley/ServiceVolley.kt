package com.appat.mvcapplication.API.Volley

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.appat.mvcapplication.Utilities.Utility
import org.json.JSONObject


class ServiceVolley : ServiceInterface {

    val TAG = ServiceVolley::class.java.simpleName

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit, failureHandler: (error: String?) -> Unit) {
        serviceCall(path, params, completionHandler, failureHandler)
    }

    override fun get(path: String, completionHandler: (response: JSONObject?) -> Unit, failureHandler: (error: String?) -> Unit) {
        serviceCall(path, null, completionHandler, failureHandler)
    }

    private fun serviceCall(path: String, params: JSONObject?, completionHandler: (response: JSONObject?) -> Unit, failureHandler: (error: String?) -> Unit) {
        if(!Utility.isNetworkAvailable())
        {
            Utility.hideLoaderDialog()
            failureHandler("No Internet Connection")
            return
        }
        val jsonRequest = object : JsonObjectRequest( path, params, Response.Listener<JSONObject> { response ->
        validateResponse(response, path, params, completionHandler, failureHandler)
        },
            Response.ErrorListener { error ->
                if(error?.networkResponse != null) {
                    Log.d("Response Error:********", path+"\n\n" + error.networkResponse)
                    if (error.networkResponse.statusCode == 500) {
                        failureHandler("Server Error")
                    } else {
                        if(error.message == null)
                        {
                            failureHandler("Server Error")
                        }
                        else {
                            failureHandler(error.message)
                        }
                    }
                }
                else {
                    Log.d("Response Error:********", path+"\n\n" + error)
                    failureHandler("Server Error")
                }
            }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return getCustomHeaders()
            }
        }
        jsonRequest.retryPolicy = DefaultRetryPolicy(
            60000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        MVCApplication.instance!!.addToRequestQueue(jsonRequest, TAG)
    }

    private fun validateResponse(response: JSONObject, path: String, params: JSONObject?, completionHandler: (response: JSONObject?) -> Unit, failureHandler: (error: String?) -> Unit)
    {
        Utility.hideLoaderDialog()
        try {
            val success = response.getBoolean("success")
            if(success)
            {
                completionHandler(response)
            }
            else
            {
                failureHandler(response.getString("message"))
            }
        } catch (e: Exception)
        {
            failureHandler("Server Error")
        }
    }

    private fun getCustomHeaders(): Map<String, String>
    {
        val headers =  HashMap<String, String>()
        return headers
    }
}