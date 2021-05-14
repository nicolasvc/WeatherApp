package com.example.weatherapp.origendatos.room.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.origendatos.room.Dao.ClimaDao
import com.example.weatherapp.origendatos.room.entidades.CiudadSeleccionadaEntity
import com.example.weatherapp.origendatos.room.entidades.ClimaCiudadDiaEntity


@Database(entities = [CiudadSeleccionadaEntity::class,ClimaCiudadDiaEntity::class], version = 2)
abstract class ClimaRoomDatabase : RoomDatabase() {
    abstract fun climaDao(): ClimaDao


    companion object {
        @Volatile
        private var instancia: ClimaRoomDatabase? = null
        private val LOCK = Any()

        fun getDataBase(context: Context): ClimaRoomDatabase = instancia ?: synchronized(LOCK) {
            instancia ?: builDataBase(context).also { instancia = it }
        }

        private fun builDataBase(context: Context) =
            Room.databaseBuilder(context, ClimaRoomDatabase::class.java, "clima_database").build()
    }

}