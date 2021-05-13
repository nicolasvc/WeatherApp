package com.example.weatherapp.origendatos.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.entidades.busquedaciudad.CiudadBuscada
import com.example.weatherapp.entidades.climaciudad.ClimaCiudad
import com.example.weatherapp.origendatos.informacionclima.ClimaClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class InformacionClimaRepositorio {

    private lateinit var climaCiudad: MutableLiveData<ClimaCiudad>
    private lateinit var listaCiudades: MutableLiveData<List<CiudadBuscada>>


    //TODO LEER COMO CONTROLAR ERROR DENTRO DE LIVEDATA
    fun obtenerClimaCiudad(idCiudad: String): MutableLiveData<ClimaCiudad> {
        climaCiudad = MutableLiveData()
        ClimaClient.obtenerCliente().consultarClimaCiudad(idCiudad)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    climaCiudad.value = it
                },
                onError = {
                    Log.i("RespuestaPost", it.toString())
                }
            )
        return climaCiudad
    }

    fun obtenerCiudades(nombreCiudad: String): MutableLiveData<List<CiudadBuscada>> {
        listaCiudades = MutableLiveData()
        ClimaClient.obtenerCliente().consultarListaCiudades(nombreCiudad)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    listaCiudades.value = it
                },
                onError = {
                    Log.i("RespuestaPost", it.toString())
                }
            )
        return listaCiudades
    }

}