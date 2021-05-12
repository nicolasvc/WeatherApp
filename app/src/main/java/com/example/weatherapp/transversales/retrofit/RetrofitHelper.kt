package com.example.weatherapp.transversales.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object {
        lateinit var retrofit: Retrofit
        var rutaUrl: String = ""

        @JvmStatic
        fun obtenerCliente(rutaNueva: String): Retrofit {
            if (rutaNueva != this.rutaUrl) {
                this.rutaUrl = rutaNueva
                retrofit = Retrofit.Builder()
                    .baseUrl(rutaNueva)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

    }

}