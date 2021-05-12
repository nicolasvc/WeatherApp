package com.example.weatherapp.vista.consultarclimaciudad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.weatherapp.R
import com.example.weatherapp.origendatos.repositorio.InformacionClimaViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.toolbar_base.*
import kotlinx.android.synthetic.main.activity_consulta_ciudad_clima.*

class ConsultaCiudadClimaActivity : AppCompatActivity() {

    private lateinit var informacionClimaViewModel: InformacionClimaViewModel

    //region Sobrecarga
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_ciudad_clima)
        configurarToolbar()
        //consultarCiudadesElegidas()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

    private fun consultarCiudadesElegidas() {
        //TODO("Not yet implemented")/

        //SI es vacio


        //Snackbar.make(coordinatorContenedor,"Por ingresa una ciudad primero",Snackbar.LENGTH_LONG).show()
    }

    private fun configurarToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}