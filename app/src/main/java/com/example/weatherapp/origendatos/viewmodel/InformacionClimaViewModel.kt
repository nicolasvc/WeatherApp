package com.example.weatherapp.origendatos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.entidades.busquedaciudad.CiudadBuscada
import com.example.weatherapp.entidades.climaciudad.ClimaCiudad
import com.example.weatherapp.origendatos.repository.InformacionClimaRepositorio

class InformacionClimaViewModel : ViewModel(){

    private val repositorioClimaCiudad  = InformacionClimaRepositorio()

    fun obtenerClimaCiudad(idCIudad:String) : LiveData<ClimaCiudad> = repositorioClimaCiudad.obtenerClimaCiudad(idCIudad)

    fun obtenerCiudades(nombreCiudad:String): LiveData<List<CiudadBuscada>> = repositorioClimaCiudad.obtenerCiudades(nombreCiudad)

}