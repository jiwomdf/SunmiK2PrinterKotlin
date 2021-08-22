package com.example.sunmik2printerkotlin.base

import android.util.Log
import com.example.sunmik2printerkotlin.BuildConfig
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

public sealed class RequestHandler<T> {
    class Success<T>(val body: T): RequestHandler<T>()
    class ErrorServer<T>(val exception: Exception, val code: String): RequestHandler<T>()
}

public abstract class BaseRepository {

    protected val SUCCESS_CODE = "200"
    protected val SUCCESS_CREATED_CODE = "201"
    protected val HTTP_NOT_AUTHORIZED_CODE = "401"

    companion object {
        private const val TAG ="BaseRepository"

        private const val DEBUG = "debug"
        private const val NO_INTERNET_CONNECTION = "No Internet Connection"
        private const val USER_UNAUTHORIZED = "User Unauthorized"
        private const val CONNECTION_TIME_OUT = "Connection Timed Out"

        fun <T> execute(response: Response<T>): RequestHandler<T> {
            try {
                return when (response.isSuccessful) {
                    true -> {
                        if (BuildConfig.BUILD_TYPE == (DEBUG))
                            Log.d(TAG, Gson().toJson(response.body()!!))
                        RequestHandler.Success(response.body()!!)
                    }
                    false -> {
                        if (BuildConfig.BUILD_TYPE == DEBUG)
                            Log.d(TAG, response.message())
                        response.errorBody()?.let {
                            val errMsg = JSONObject(it.string()).getString("message")
                            return RequestHandler.ErrorServer(
                                IOException(errMsg),
                                response.code().toString()
                            )
                        } ?:
                        return RequestHandler.ErrorServer(
                            IOException(response.message()),
                            response.code().toString()
                        )
                    }
                }
            } catch (e: Exception) {
                if (BuildConfig.BUILD_TYPE == DEBUG)
                    e.message?.let {
                        Log.d(TAG, it)
                    }
                when (e) {
                    is SocketTimeoutException -> {
                        throw SocketTimeoutException(CONNECTION_TIME_OUT)
                    }
                    is UnknownHostException -> {
                        throw UnknownHostException(NO_INTERNET_CONNECTION)
                    }
                    else -> {
                        Log.e(TAG, e.message.toString())
                        throw Exception(e.message.toString())
                    }
                }
            }
        }
    }
}
