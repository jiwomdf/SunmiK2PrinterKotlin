package com.example.sunmik2printerkotlin

import com.example.sunmik2printerkotlin.remote.json.PostCounterResponse
import com.example.sunmik2printerkotlin.remote.json.QueueNumberResponse
import com.example.sunmik2printerkotlin.util.Event

interface MainPresenterView {
    fun onSuccessGetQueue(result: Event.Success<QueueNumberResponse>)
    fun onLoadingGetQueue()
    fun onFailedGetQueue(error: Event.Error)
}