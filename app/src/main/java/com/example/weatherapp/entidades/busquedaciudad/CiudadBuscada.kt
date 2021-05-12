package com.example.weatherapp.entidades.busquedaciudad


import com.google.gson.annotations.SerializedName

open class CiudadBuscada(
    @SerializedName("latt_long")
    val lattLong: String, // 37.777119, -122.41964
    @SerializedName("location_type")
    val locationType: String, // City
    @SerializedName("title")
    val title: String, // San Francisco
    @SerializedName("woeid")
    val woeid: Int // 2487956
)