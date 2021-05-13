package com.example.weatherapp.origendatos.repository

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import com.example.weatherapp.di.application.MyApp
import com.example.weatherapp.origendatos.room.Dao.ClimaDao
import com.example.weatherapp.origendatos.room.DataBase.ClimaRoomDatabase
import com.example.weatherapp.origendatos.room.entidades.CiudadSeleccionadaEntity
import java.util.concurrent.Executors

class ClimaRepositorio {

    private var climaDao: ClimaDao? = null
    private var listaCiudadesSeleccionadas: LiveData<List<CiudadSeleccionadaEntity>>? = null

    init {
        val database = ClimaRoomDatabase.getDataBase(MyApp.applicationContext())
        climaDao = database.climaDao()
        listaCiudadesSeleccionadas = climaDao!!.getAll()
    }

    fun updateClima(ciudadModificada: CiudadSeleccionadaEntity) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            climaDao?.insertCiudad(ciudadModificada)
        }
    }

    fun eliminarAllCiudades(){
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

    fun getAllCiudades(): LiveData<List<CiudadSeleccionadaEntity>> = listaCiudadesSeleccionadas!!


}