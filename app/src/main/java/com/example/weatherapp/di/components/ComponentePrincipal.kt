package com.example.weatherapp.di.components

import com.example.weatherapp.di.module.ModuloPrincipal
import com.example.weatherapp.ui.consultarclimaciudad.ConsultaCiudadClimaActivity
import com.example.weatherapp.ui.informacionclima.InformacionClimaActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ModuloPrincipal::class])
interface ComponentePrincipal {

    fun inject(informacionClimaActivity: InformacionClimaActivity)

    fun inject(consultaCiudadClimaActivity: ConsultaCiudadClimaActivity)

}