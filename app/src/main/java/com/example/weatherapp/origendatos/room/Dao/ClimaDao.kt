package com.example.weatherapp.origendatos.room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherapp.origendatos.room.entidades.CiudadConClimaDataClass
import com.example.weatherapp.origendatos.room.entidades.CiudadSeleccionadaEntity
import com.example.weatherapp.origendatos.room.entidades.ClimaCiudadDiaEntity


@Dao
interface ClimaDao {

    @Query("SELECT * FROM CiudadesSeleccionadas ORDER BY idCiudad ASC")
    fun getAll(): LiveData<List<CiudadSeleccionadaEntity>>

    @Insert
    fun insertCiudad(ciudadSeleccionadaEntity: CiudadSeleccionadaEntity)

    @Update
    fun updateCiudad(ciudadSeleccionadaEntity: CiudadSeleccionadaEntity)

    @Query("DELETE FROM CiudadesSeleccionadas where idCiudad = :city")
    fun deletebyId(city: Int)

    @Query("DELETE FROM CiudadesSeleccionadas")
    fun deleteAll()



    @Transaction
    @Query("SELECT * FROM CiudadesSeleccionadas")
    fun getAllCiudadConClima():LiveData<List<CiudadConClimaDataClass>>

    @Insert
    fun insertClimaCiudad(ciudadConClimaDataClass: ClimaCiudadDiaEntity)

    @Update
    fun updateClimaCiudad(ciudadConClimaDataClass: ClimaCiudadDiaEntity)

    @Query("DELETE FROM ClimaCiudadDia")
    fun deleteClimaPorCiuda()
}