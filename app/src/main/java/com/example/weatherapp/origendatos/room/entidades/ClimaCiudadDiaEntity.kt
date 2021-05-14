package com.example.weatherapp.origendatos.room.entidades
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.entidades.climaciudad.ConsolidatedWeather

@Entity(tableName = "ClimaCiudadDia")
data class ClimaCiudadDiaEntity(
    val airPressure: Double,
    val applicableDate: String,
    val created: String,
    val humidity: Int,
    val maxTemp: Double,
    val minTemp: Double,
    val predictability: Int,
    val theTemp: Double,
    val visibility: Double,
    val weatherStateAbbr: String,
    val weatherStateName: String,
    val windDirection: Double,
    val windSpeed: Double,
    val windDirectionCompass: String,
    val woeidCiudad:String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}