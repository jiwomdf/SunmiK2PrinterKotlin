package com.example.sunmik2printerkotlin.remote.service

import com.example.sunmik2printerkotlin.remote.json.PostCounterResponse
import com.example.sunmik2printerkotlin.remote.json.QueueNumberResponse
import com.example.sunmik2printerkotlin.remote.request.PostCounterRequest
import com.example.sunmik2printerkotlin.remote.request.QueueNumberRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @POST("")
    fun postCounter(
        @Body request: PostCounterRequest
    ): Response<PostCounterResponse>

    @GET("")
    fun getQueueNumber(
        @Body request: QueueNumberRequest
    ): Response<QueueNumberResponse>
}