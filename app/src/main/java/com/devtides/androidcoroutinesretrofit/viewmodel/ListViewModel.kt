package com.devtides.androidcoroutinesretrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devtides.androidcoroutinesretrofit.model.CountriesService
import com.devtides.androidcoroutinesretrofit.model.Country
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ListViewModel: ViewModel() {

    val countriesService = CountriesService.generateRetrofitApi()
    val exceptionHandler = CoroutineExceptionHandler{ coroutineContext: CoroutineContext, throwable: Throwable ->

        onError("Exception: ${throwable.localizedMessage}")
    }
    var job : Job? = null


    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()


    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch{
            val response =countriesService.getCountriesAsync()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    countries.value = response.body()
                    countryLoadError.value = ""
                    loading.value = false
                }else{
                    onError("Error: ${response.message()}")
                }
            }
        }

//        val dummyData = generateDummyCountries()
//
//        countries.value = dummyData
//        countryLoadError.value = ""
//        loading.value = false
    }

    private fun generateDummyCountries(): List<Country> {

        val countries = arrayListOf<Country>()
        countries.add(Country("dummyCountry1",  "dummyCapital1",""))
        countries.add(Country("dummyCountry2",  "dummyCapital2",""))
        countries.add(Country("dummyCountry3",  "dummyCapital3",""))
        countries.add(Country("dummyCountry4",  "dummyCapital4",""))
        countries.add(Country("dummyCountry5",  "dummyCapital5",""))
        return countries
    }

    private fun onError(message: String) {
        countryLoadError.value = message
        loading.value = false
    }

}