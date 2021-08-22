package com.example.sunmik2printerkotlin.remote.service

import com.example.sunmik2printerkotlin.remote.json.ResponseClass
import com.example.sunmik2printerkotlin.remote.request.RequestClass
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {

    @POST("")
    fun postCounter(
        @Body request: RequestClass
    ): Response<ResponseClass>
}