package com.example.sunmik2printerkotlin

import com.example.sunmik2printerkotlin.remote.json.ResponseClass
import com.example.sunmik2printerkotlin.util.Event

interface MainPresenterView {
    fun onSuccess(result: Event.Success<ResponseClass>)
    fun onLoading()
    fun onFailed(error: Event.Error)
}