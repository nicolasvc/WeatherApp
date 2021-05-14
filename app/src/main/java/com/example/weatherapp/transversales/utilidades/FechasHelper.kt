package com.example.weatherapp.transversales.utilidades

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class FechasHelper {

    companion object {
        fun obtenerInstancia() = FechasHelper()
    }

    @SuppressLint("SimpleDateFormat")
    fun obtenerFechaConsulta(fecha: String): String {
        val currentTime: String = SimpleDateFormat("hh:mm", Locale.getDefault()).format(Date())
        val date: Date? = SimpleDateFormat("yyyy-M-d").parse(fecha)
        val diaSemana = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date!!).substring(0, 3)
        return String.format("%s, %s", diaSemana, currentTime)
    }

    @SuppressLint("SimpleDateFormat")
     fun obtenerDiaSemana(fecha: String): String {
        val date: Date? = SimpleDateFormat("yyyy-M-d").parse(fecha)
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(date!!).substring(0, 3)
    }

    @SuppressLint("SimpleDateFormat")
     fun obtenerMes(fecha: String): String {
        val date: Date? = SimpleDateFormat("yyyy-M-d").parse(fecha)
        val cal = Calendar.getInstance()
        cal.time = date!!
        val monthName = SimpleDateFormat("MMMM").format(cal.time).substring(0, 3)
        return String.format("%s %s ",monthName.substring(0, 1).uppercase() + monthName.substring(1).lowercase() , cal.get(Calendar.DAY_OF_MONTH))
    }
}