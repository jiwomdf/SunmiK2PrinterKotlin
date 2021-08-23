package com.example.sunmik2printerkotlin.remote.service

import com.example.sunmik2printerkotlin.remote.json.PostCounterResponse
import com.example.sunmik2printerkotlin.remote.json.QueueNumberResponse
import com.example.sunmik2printerkotlin.remote.request.PostCounterRequest
import com.example.sunmik2printerkotlin.remote.request.QueueNumberRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Service {

    @GET("/service/medifirst2000/bridging/anta/get-print-noantrian?")
    suspend fun getQueueNumber(
        @Query("norec") norec : String
    ): Response<QueueNumberResponse>
}