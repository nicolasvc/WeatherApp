package com.example.weatherapp.entidades.busquedaciudad

class CiudadElegida(
    val temperaturaCiudad:Int,
    val estadoClima:String,
    ciudadBuscada: CiudadBuscada
) : CiudadBuscada(ciudadBuscada.lattLong, ciudadBuscada.locationType, ciudadBuscada.title, ciudadBuscada.woeid) {

}