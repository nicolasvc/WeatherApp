package com.example.weatherapp.origendatos.room.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CiudadesSeleccionadas")
data class CiudadSeleccionadaEntity(
    val temperaturaCiudad:Int,
    val estadoClima:String,
    val woeidCiudad:String,
    val nombreciudad:String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
