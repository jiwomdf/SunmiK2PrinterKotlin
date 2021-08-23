package com.example.sunmik2printerkotlin

import com.example.sunmik2printerkotlin.remote.ApiResponses
import com.example.sunmik2printerkotlin.remote.request.PostCounterRequest
import com.example.sunmik2printerkotlin.remote.request.QueueNumberRequest
import com.example.sunmik2printerkotlin.repository.Repository
import com.example.sunmik2printerkotlin.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenter(
    private val postCounterView: MainPresenterView,
    private val repository: Repository
) {

    fun getQueueNumber(norec: String) = CoroutineScope(Dispatchers.Default).launch {
        postCounterView.onLoadingGetQueue()
        when(val response = repository.getQueueNumber(norec)) {
            is ApiResponses.Success -> {
                withContext(Dispatchers.Main) {
                    postCounterView.onSuccessGetQueue(Event.Success(response.data))
                }
            }
            is ApiResponses.Error -> {
                withContext(Dispatchers.Main) {
                    postCounterView.onFailedGetQueue(Event.Error(response.errorMessage))
                }
            }
        }
    }

}