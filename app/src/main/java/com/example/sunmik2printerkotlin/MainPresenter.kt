package com.example.sunmik2printerkotlin

import com.example.sunmik2printerkotlin.remote.ApiResponses
import com.example.sunmik2printerkotlin.remote.json.ResponseClass
import com.example.sunmik2printerkotlin.remote.request.RequestClass
import com.example.sunmik2printerkotlin.repository.Repository
import com.example.sunmik2printerkotlin.util.Event

class MainPresenter(
    private val presenterView: MainPresenterView,
    private val repository: Repository
) {

    fun postCounter(requestClass: RequestClass) {
        presenterView.onLoading()
        when(val response = repository.postCounter(requestClass)) {
            is ApiResponses.Success -> {
                presenterView.onSuccess(Event.Success(response.data))
            }
            is ApiResponses.Error -> {
                presenterView.onFailed(Event.Error(response.errorMessage))
            }
        }
    }


}