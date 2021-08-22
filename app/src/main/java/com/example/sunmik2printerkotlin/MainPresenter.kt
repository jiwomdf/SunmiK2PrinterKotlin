package com.example.sunmik2printerkotlin

import com.example.sunmik2printerkotlin.remote.ApiResponses
import com.example.sunmik2printerkotlin.remote.request.PostCounterRequest
import com.example.sunmik2printerkotlin.remote.request.QueueNumberRequest
import com.example.sunmik2printerkotlin.repository.Repository
import com.example.sunmik2printerkotlin.util.Event

class MainPresenter(
    private val postCounterView: MainPresenterView,
    private val repository: Repository
) {

    fun postCounter(postCounterRequest: PostCounterRequest) {
        postCounterView.onLoadingPostData()
        when(val response = repository.postCounter(postCounterRequest)) {
            is ApiResponses.Success -> {
                postCounterView.onSuccessPostData(Event.Success(response.data))
            }
            is ApiResponses.Error -> {
                postCounterView.onFailedPostData(Event.Error(response.errorMessage))
            }
        }
    }

    fun getQueueNumber(queueNumberRequest: QueueNumberRequest) {
        postCounterView.onLoadingGetQueue()
        when(val response = repository.getQueueNumber(queueNumberRequest)) {
            is ApiResponses.Success -> {
                postCounterView.onSuccessGetQueue(Event.Success(response.data))
            }
            is ApiResponses.Error -> {
                postCounterView.onFailedGetQueue(Event.Error(response.errorMessage))
            }
        }
    }

}