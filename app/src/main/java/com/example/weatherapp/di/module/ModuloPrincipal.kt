package com.example.weatherapp.di.module

import android.content.Context
import com.example.weatherapp.di.application.MyApp
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ModuloPrincipal @Inject constructor( val aplicacionPrincipal: MyApp) {

    @Singleton
    @Provides
    internal fun obtenerContextApp(): Context = aplicacionPrincipal
}