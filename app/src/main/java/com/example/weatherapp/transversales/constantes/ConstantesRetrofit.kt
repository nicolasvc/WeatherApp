package com.example.weatherapp.transversales.constantes

class ConstantesRetrofit {

    companion object {
        const val API_BASE_CLIMA ="https://www.metaweather.com/api/"
        const val API_CONSULTA_CIUDAD ="location/search/"
        const val API_CONSULTA_CLIMA_CIUDAD ="location/{idCiudad}"
        const val API_FILES_URL= "https://www.metaweather.com/static/img/weather/%s.svg"
    }
}