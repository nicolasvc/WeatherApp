package com.example.weatherapp.origendatos.room.entidades

import androidx.room.Embedded
import androidx.room.Relation


data class CiudadConClimaDataClass (
    @Embedded
    val ciudad: CiudadSeleccionadaEntity,
    @Relation(
        parentColumn = "idCiudad",
        entityColumn = "woeidCiudad"
    )
    val listaClimaCiudadEntity: List<ClimaCiudadDiaEntity>
)

