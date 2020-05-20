package com.appat.mvcapplication.Models.ResponseModel.CustomerLogin
//
//  CustomerLoginData.kt
//
//  Generated using https://jsonmaster.github.io
//  Created on May 20, 2020
//

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

data class CustomerLoginData (
		@SerializedName("pkUserId") val pkUserId: String,
		@SerializedName("strUserName") val strUserName: String,
		@SerializedName("strEmail") val strEmail: String,
		@SerializedName("strPhone") val strPhone: String
) {
	companion object { 
		fun create(json: String): CustomerLoginData {
			val gson = GsonBuilder().create()
			return gson.fromJson(json, CustomerLoginData::class.java)
		}
	}

	override fun toString(): String {
		val gson = GsonBuilder().create()
		return gson.toJson(this)
	}
}