package com.devtides.androidcoroutinesretrofit.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName( "name")
    val countryName:String?,
    @SerializedName("capital")
    val capital:String?,
    @SerializedName("flagPNG")
    val flagImagePath:String?
)