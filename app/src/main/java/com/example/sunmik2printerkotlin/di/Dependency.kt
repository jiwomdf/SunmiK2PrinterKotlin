package com.example.sunmik2printerkotlin.di

import com.example.sunmik2printerkotlin.remote.service.Service
import com.example.sunmik2printerkotlin.repository.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Dependency {

    const val BASE_URL = ""

    private fun provideRepository(service: Service): Repository {
        return Repository(service)
    }

    private fun provideService(retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }

    private fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun provideOkhttpClient() : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    fun inject(): Repository {
        return provideRepository(provideService(provideRetrofit(provideOkhttpClient())))
    }
}