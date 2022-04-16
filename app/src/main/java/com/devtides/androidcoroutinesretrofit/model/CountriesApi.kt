package com.devtides.androidcoroutinesretrofit.model

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface CountriesApi {
    @GET("DevTides/countries/master/countriesV2.json")
    suspend fun getCountriesAsync():Response<List<Country>>
}