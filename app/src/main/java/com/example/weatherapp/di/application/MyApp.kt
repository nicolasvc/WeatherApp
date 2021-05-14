package com.example.weatherapp.di.application

import android.app.Application
import android.content.Context
import com.example.weatherapp.di.components.ComponentePrincipal
import com.example.weatherapp.di.components.DaggerComponentePrincipal
import com.example.weatherapp.di.module.ModuloPrincipal
import com.facebook.drawee.backends.pipeline.Fresco

class MyApp : Application() {

    private lateinit var aplicacionComponente: ComponentePrincipal

    init {
        instance = this
    }

    companion object {
        private var instance: MyApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        aplicacionComponente = DaggerComponentePrincipal.builder().moduloPrincipal(ModuloPrincipal(this)).build()
    }

    fun getComponent(): ComponentePrincipal = aplicacionComponente
}