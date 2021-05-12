package com.example.weatherapp.origendatos.informacionclima

import com.example.weatherapp.entidades.busquedaciudad.CiudadBuscada
import com.example.weatherapp.entidades.climaciudad.ClimaCiudad
import com.example.weatherapp.transversales.constantes.ConstantesRetrofit
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClimaOrigenDatos {

    @GET(ConstantesRetrofit.API_CONSULTA_CIUDAD)
    fun consultarListaCiudades(@Query("query") nombreCiudad:String): Single<List<CiudadBuscada>>

    @GET(ConstantesRetrofit.API_CONSULTA_CLIMA_CIUDAD)
    fun consultarClimaCiudad(@Path("idCiudad") idCiudad:String): Single<ClimaCiudad>
}