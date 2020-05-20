package com.appat.mvcapplication.API.Volley

import org.json.JSONObject

class APIController constructor(serviceInjection: ServiceInterface): ServiceInterface {

    private val service: ServiceInterface = serviceInjection

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit, failureHandler: (error: String?) -> Unit) {
        service.post(path, params, completionHandler, failureHandler)
    }

    override fun get(path: String, completionHandler: (response: JSONObject?) -> Unit, failureHandler: (error: String?) -> Unit) {
        service.get(path, completionHandler, failureHandler)
    }

    companion object {
        fun instance(): APIController {
            val service = ServiceVolley()
            return APIController(service)
        }
    }
}