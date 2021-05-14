package com.example.weatherapp.origendatos.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.di.application.MyApp
import com.example.weatherapp.origendatos.room.Dao.ClimaDao
import com.example.weatherapp.origendatos.room.DataBase.ClimaRoomDatabase
import com.example.weatherapp.origendatos.room.entidades.CiudadConClimaDataClass
import com.example.weatherapp.origendatos.room.entidades.CiudadSeleccionadaEntity
import com.example.weatherapp.origendatos.room.entidades.ClimaCiudadDiaEntity
import java.util.concurrent.Executors

class ClimaRepositorio {

    private var climaDao: ClimaDao? = null
    private var listaCiudadesSeleccionadas: LiveData<List<CiudadSeleccionadaEntity>>? = null
    private var listaClimaCiudades: LiveData<List<CiudadConClimaDataClass>>? = null

    init {
        val database = ClimaRoomDatabase.getDataBase(MyApp.applicationContext())
        climaDao = database.climaDao()
        listaCiudadesSeleccionadas = climaDao!!.getAll()
        listaClimaCiudades = climaDao!!.getAllCiudadConClima()
    }

    fun updateClima(ciudadModificada: CiudadSeleccionadaEntity) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            climaDao?.updateCiudad(ciudadModificada)
        }
    }

    fun eliminarAllCiudades() {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            climaDao?.deleteAll()
        }
    }

    fun insertarClima(ciudadNueva: CiudadSeleccionadaEntity) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            climaDao?.insertCiudad(ciudadNueva)
        }
    }

    fun insertarClimaCiudad(climaCiudadDiaEntity: ClimaCiudadDiaEntity){
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            climaDao?.insertClimaCiudad(climaCiudadDiaEntity)
        }
    }

    fun eliminarCiudadPorId(idCiudad:String){
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            climaDao?.deletebyId(idCiudad.toInt())
        }
    }

    fun eleminarClimaCiudades(idCiudad:String){
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            climaDao?.deleteClimaPorCiuda(idCiudad)
        }
    }

    fun eliminarAllClimaCiudades(){
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            climaDao?.deleteAllClimaCiudad()
        }
    }

    fun getAllCiudadesConClima(): LiveData<List<CiudadConClimaDataClass>> = listaClimaCiudades!!

    fun getAllCiudades(): LiveData<List<CiudadSeleccionadaEntity>> = listaCiudadesSeleccionadas!!


}