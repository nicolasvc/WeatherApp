package com.example.weatherapp.origendatos.room.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.origendatos.room.Dao.ClimaDao
import com.example.weatherapp.origendatos.room.entidades.CiudadSeleccionadaEntity


@Database(entities = [CiudadSeleccionadaEntity::class], version = 1)
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
            Room.databaseBuilder(context, ClimaRoomDatabase::class.java, "notas_database").build()
    }

}