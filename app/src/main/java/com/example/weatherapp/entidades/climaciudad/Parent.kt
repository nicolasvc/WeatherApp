package com.example.weatherapp.entidades.climaciudad


import com.google.gson.annotations.SerializedName

data class Parent(
    @SerializedName("latt_long")
    val lattLong: String, // 52.883560,-1.974060
    @SerializedName("location_type")
    val locationType: String, // Region / State / Province
    @SerializedName("title")
    val title: String, // England
    @SerializedName("woeid")
    val woeid: Int // 24554868
)