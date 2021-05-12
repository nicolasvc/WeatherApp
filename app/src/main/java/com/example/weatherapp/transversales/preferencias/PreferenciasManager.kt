package com.example.weatherapp.transversales.preferencias

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import com.example.weatherapp.di.application.MyApp
import com.google.gson.Gson

class PreferenciasManager {

    //region Propiedades
    private val APP_SETTING_FILE = "APP_SETTINGS"
    private val gson = Gson();
    private val preferenciasDispositivo : SharedPreferences = getSharedPreference()
    //endregion

    //region Companion
    companion object {
        fun obtenerInstancia(): PreferenciasManager = PreferenciasManager()
    }
    //endregion


    //region Metodos propios
    private fun getSharedPreference(): SharedPreferences =
        MyApp.applicationContext().getSharedPreferences(APP_SETTING_FILE, Context.MODE_PRIVATE)


    fun almacenar(@NonNull llave: String?, @NonNull objeto: Any?) {
        llave!!.trim { it <= ' ' }
        if (llave == "") throw IllegalArgumentException("No se puede pasar una llave vacia")
        if (llave == null || objeto == null) throw IllegalArgumentException("No se puede pasar un objeto vacio")
        preferenciasDispositivo.edit()
            .putString(llave, gson.toJson(objeto))
            .apply()
    }



    fun <T> obtener(@NonNull llave: String?, tipoClase: Class<T>?): T? {
        if (llave == "") throw IllegalArgumentException("No se puede pasar una llave vacia")
        if (llave == null) throw IllegalArgumentException("No se puede pasar  una llave nula")
        val json = preferenciasDispositivo.getString(llave, null)
        return if (json == null) {
            null
        } else {
            try {
                gson.fromJson(json, tipoClase)
            } catch (ex: Exception) {
                throw RuntimeException("No es posible obtener la clase ")
            }
        }
    }
    //endregion


}