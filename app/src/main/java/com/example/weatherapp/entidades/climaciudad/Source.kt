package com.example.weatherapp.entidades.climaciudad


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("crawl_rate")
    val crawlRate: Int, // 360
    @SerializedName("slug")
    val slug: String, // bbc
    @SerializedName("title")
    val title: String, // BBC
    @SerializedName("url")
    val url: String // http://www.bbc.co.uk/weather/
)