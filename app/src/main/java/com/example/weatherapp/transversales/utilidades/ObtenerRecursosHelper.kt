package com.example.weatherapp.transversales.utilidades

import com.example.weatherapp.R
import com.example.weatherapp.ui.informacionclima.WeatherStatesEnum

class ObtenerRecursosHelper {


    companion object{
        fun obtenerInstancia(): ObtenerRecursosHelper = ObtenerRecursosHelper()
    }


     fun obtenerDrawableBackgroundClima(weatherStateAbbr: String): Int {
        return when (weatherStateAbbr) {
            WeatherStatesEnum.sn.name -> R.drawable.background_dia_frio
            WeatherStatesEnum.sl.name -> R.drawable.background_tormenta
            WeatherStatesEnum.h.name -> R.drawable.background_tormenta
            WeatherStatesEnum.t.name -> R.drawable.background_tormenta
            WeatherStatesEnum.hr.name -> R.drawable.backgroundnight
            WeatherStatesEnum.lr.name -> R.drawable.backgroundnight
            WeatherStatesEnum.s.name -> R.drawable.backgroundnight
            WeatherStatesEnum.hc.name -> R.drawable.background_dia_frio
            WeatherStatesEnum.lc.name -> R.drawable.background_clear
            WeatherStatesEnum.c.name -> R.drawable.background_clear
            else -> R.drawable.background_dia_colorado
        }
    }


     fun obtenerDrawableIconosClima(weatherStateAbbr: String): Int {
        return when(weatherStateAbbr){
            WeatherStatesEnum.sn.name-> R.drawable.ic_sn
            WeatherStatesEnum.sl.name-> R.drawable.ic_sl
            WeatherStatesEnum.h.name-> R.drawable.ic_h
            WeatherStatesEnum.t.name-> R.drawable.ic_t
            WeatherStatesEnum.hr.name-> R.drawable.ic_hr
            WeatherStatesEnum.lr.name-> R.drawable.ic_lr
            WeatherStatesEnum.s.name-> R.drawable.ic_s
            WeatherStatesEnum.hc.name-> R.drawable.ic_hc
            WeatherStatesEnum.lc.name-> R.drawable.ic_lc
            WeatherStatesEnum.c.name-> R.drawable.ic_c
            else -> R.drawable.ic_c
        }
    }
}