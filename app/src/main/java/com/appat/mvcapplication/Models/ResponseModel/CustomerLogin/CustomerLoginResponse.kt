package com.appat.mvcapplication.Models.ResponseModel.CustomerLogin
//
//  TestResponse.kt
//
//  Generated using https://jsonmaster.github.io
//  Created on May 20, 2020
//

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

data class CustomerLoginResponse (
		@SerializedName("success") val success: Boolean,
		@SerializedName("message") val message: String,
		@SerializedName("data") val dataField: List<CustomerLoginData>
) {
	companion object { 
		fun create(json: String): CustomerLoginResponse {
			val gson = GsonBuilder().create()
			return gson.fromJson(json, CustomerLoginResponse::class.java)
		}
	}

	override fun toString(): String {
		val gson = GsonBuilder().create()
		return gson.toJson(this)
	}
}