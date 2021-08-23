package com.example.sunmik2printerkotlin.repository

import com.example.sunmik2printerkotlin.base.BaseRepository
import com.example.sunmik2printerkotlin.base.RequestHandler
import com.example.sunmik2printerkotlin.remote.ApiResponses
import com.example.sunmik2printerkotlin.remote.json.PostCounterResponse
import com.example.sunmik2printerkotlin.remote.json.QueueNumberResponse
import com.example.sunmik2printerkotlin.remote.request.PostCounterRequest
import com.example.sunmik2printerkotlin.remote.request.QueueNumberRequest
import com.example.sunmik2printerkotlin.remote.service.Service

class Repository(private val service: Service): BaseRepository() {

    suspend fun getQueueNumber(norec: String): ApiResponses<QueueNumberResponse> {
        return try {
            when(val response = execute(service.getQueueNumber(norec))){
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