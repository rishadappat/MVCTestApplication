package com.appat.mvcapplication.API.APIClasses

import com.appat.mvcapplication.API.Volley.APIController
import com.appat.mvcapplication.Models.RequestModel.CustomerLoginRequest
import com.appat.mvcapplication.Models.ResponseModel.CustomerLogin.CustomerLoginResponse
import com.appat.mvcapplication.Utilities.Utility

object UserManagementAPI
{
    fun UserLogin(params: CustomerLoginRequest, completionHandler: (response: CustomerLoginResponse?) -> Unit, failureHandler: (error: String?) -> Unit)
    {
        val serviceUrl = Utility.getBaseURL() + "login/CustomerLogin"
        APIController.instance().post(serviceUrl, params.toJSONObject(), {
            completionHandler(CustomerLoginResponse.create(it.toString()))
        }, failureHandler)
    }
}