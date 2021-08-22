package com.example.sunmik2printerkotlin.repository

import com.example.sunmik2printerkotlin.base.BaseRepository
import com.example.sunmik2printerkotlin.base.RequestHandler
import com.example.sunmik2printerkotlin.remote.ApiResponses
import com.example.sunmik2printerkotlin.remote.json.ResponseClass
import com.example.sunmik2printerkotlin.remote.request.RequestClass
import com.example.sunmik2printerkotlin.remote.service.Service

class Repository(private val service: Service): BaseRepository() {

    fun postCounter(requestClass: RequestClass): ApiResponses<ResponseClass> {
        return try {
            when(val response = execute(service.postCounter(requestClass))){
                is RequestHandler.Success -> {
                    ApiResponses.Success(response.body)
                }
                is RequestHandler.ErrorServer -> {
                    if(response.code == HTTP_NOT_AUTHORIZED_CODE){
                        ApiResponses.NotAuthorized
                    } else {
                        ApiResponses.Error(response.exception.message.toString())
                    }
                }
            }
        } catch (ex: Exception){
            ApiResponses.Error(ex.message.toString())
        }
    }

}