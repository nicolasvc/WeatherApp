package com.example.weatherapp.origendatos.room.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CiudadesSeleccionadas")
data class CiudadSeleccionadaEntity(
    val temperaturaCiudad: Int,
    val estadoClima: String,
    @PrimaryKey val idCiudad: String,
    val nombreciudad: String,
    val abreviaturaClima:String
)


