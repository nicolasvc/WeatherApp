package com.example.weatherapp.origendatos.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherapp.origendatos.repository.ClimaRepositorio
import com.example.weatherapp.origendatos.room.entidades.CiudadSeleccionadaEntity
import com.example.weatherapp.origendatos.room.entidades.ClimaCiudadDiaEntity

class CiudadElegidaViewModel :ViewModel() {

    private val climaRepositorio : ClimaRepositorio = ClimaRepositorio()

    fun getAllCiudades() = climaRepositorio.getAllCiudades()

    fun insertCiudad(ciudadSeleccionadaEntity: CiudadSeleccionadaEntity) = climaRepositorio.insertarClima(ciudadSeleccionadaEntity)

    fun updateCiudad(ciudadSeleccionadaEntity: CiudadSeleccionadaEntity) = climaRepositorio.updateClima(ciudadSeleccionadaEntity)

    fun deleteAllCiudades()= climaRepositorio.eliminarAllCiudades()

    fun getAllCiudadesClima() = climaRepositorio.getAllCiudadesConClima()

    fun insertClimaCiudad(climaCiudadDiaEntity: ClimaCiudadDiaEntity) = climaRepositorio.insertarClimaCiudad(climaCiudadDiaEntity)

    fun eliminarClimaCiudad() = climaRepositorio.eliminarClimaPorCiudad()
}