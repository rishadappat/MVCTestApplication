package com.appat.mvcapplication.Models.RequestModel

import com.google.gson.annotations.SerializedName
import com.google.gson.GsonBuilder
import org.json.JSONObject

data class CustomerLoginRequest (
@SerializedName("strEmail") val strEmail: String,
@SerializedName("strPassword") val strPassword: String,
@SerializedName("strUserId") val strUserId: String
) {
    override fun toString(): String {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }

    fun toJSONObject(): JSONObject {
        val gson = GsonBuilder().create()
        return JSONObject(gson.toJson(this))
    }
}