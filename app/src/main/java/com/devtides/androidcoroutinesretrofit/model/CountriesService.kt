package com.devtides.androidcoroutinesretrofit.model

import com.bumptech.glide.BuildConfig
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CountriesService {
    val BASE_URL = "https://raw.githubusercontent.com"
    private val api :CountriesApi

    init {
        api=generateRetrofitApi()
    }

    fun generateRetrofitApi(): CountriesApi {
        //Http log
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        //Http Builder
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.interceptors().add(loggingInterceptor)
        //Http client
        val client = clientBuilder
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }

}