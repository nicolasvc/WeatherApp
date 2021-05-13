package com.example.weatherapp.origendatos.room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.origendatos.room.entidades.CiudadSeleccionadaEntity


@Dao
interface ClimaDao {

    @Query("SELECT * FROM CiudadesSeleccionadas ORDER BY woeidCiudad ASC")
    fun getAll(): LiveData<List<CiudadSeleccionadaEntity>>

    @Insert
    fun insertCiudad(nota: CiudadSeleccionadaEntity)

    @Update
    fun updateCiudad(nota: CiudadSeleccionadaEntity)

    @Query("DELETE FROM CiudadesSeleccionadas where id = :city")
    fun deletebyId(city: Int)

    @Query("DELETE FROM CiudadesSeleccionadas")
    fun deleteAll()

}