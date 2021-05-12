package com.example.weatherapp.entidades.climaciudad


import com.google.gson.annotations.SerializedName

data class ConsolidatedWeather(
    @SerializedName("air_pressure")
    val airPressure: Double, // 1008.0
    @SerializedName("applicable_date")
    val applicableDate: String, // 2021-05-12
    @SerializedName("created")
    val created: String, // 2021-05-12T00:32:04.067248Z
    @SerializedName("humidity")
    val humidity: Int, // 52
    @SerializedName("id")
    val id: Long, // 6551515415183360
    @SerializedName("max_temp")
    val maxTemp: Double, // 16.215
    @SerializedName("min_temp")
    val minTemp: Double, // 8.3
    @SerializedName("predictability")
    val predictability: Int, // 70
    @SerializedName("the_temp")
    val theTemp: Double, // 16.34
    @SerializedName("visibility")
    val visibility: Double, // 12.958385528513482
    @SerializedName("weather_state_abbr")
    val weatherStateAbbr: String, // lc
    @SerializedName("weather_state_name")
    val weatherStateName: String, // Light Cloud
    @SerializedName("wind_direction")
    val windDirection: Double, // 208.82493998358174
    @SerializedName("wind_direction_compass")
    val windDirectionCompass: String, // SSW
    @SerializedName("wind_speed")
    val windSpeed: Double // 6.566554854222011
)