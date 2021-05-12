package com.example.weatherapp.origendatos.informacionclima

import com.example.weatherapp.transversales.constantes.ConstantesRetrofit
import com.example.weatherapp.transversales.retrofit.RetrofitHelper

class ClimaClient {

    companion object {
        fun obtenerCliente(): ClimaOrigenDatos =
            RetrofitHelper.obtenerCliente(ConstantesRetrofit.API_BASE_CLIMA)
                .create(ClimaOrigenDatos::class.java)

    }
}