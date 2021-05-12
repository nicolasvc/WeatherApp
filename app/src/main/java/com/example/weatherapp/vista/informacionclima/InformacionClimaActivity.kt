package com.example.weatherapp.vista.informacionclima

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.entidades.busquedaciudad.CiudadBuscada
import com.example.weatherapp.entidades.climaciudad.ClimaCiudad
import com.example.weatherapp.entidades.climaciudad.ConsolidatedWeather
import com.example.weatherapp.origendatos.repositorio.InformacionClimaViewModel
import com.example.weatherapp.transversales.constantes.CodigoIntencion
import com.example.weatherapp.transversales.constantes.ConstantesPreferencias
import com.example.weatherapp.transversales.preferencias.PreferenciasManager
import com.example.weatherapp.vista.consultarclimaciudad.ConsultaCiudadClimaActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.grid_layout_detalle_clima.*
import kotlinx.android.synthetic.main.toolbar_base.*


class InformacionClimaActivity : AppCompatActivity() {

    private lateinit var informacionClimaViewModel: InformacionClimaViewModel

    //region Sobrecarga
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configurarToolbar()
        iniciarViewModel()
        verificarCiudadElegida()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_busqueda_ciudad, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.agregarCiudad)
            navegarSeleccionCiudad()
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == CodigoIntencion.CODIGO_INTENCION_OBTENER_CIUDAD)
            data?.getStringExtra("IdCiudad")?.let { consultarClimaCiudad(it) }
        super.onActivityResult(requestCode, resultCode, data)
    }
    //endregion


    //region Metodos propios
    private fun iniciarViewModel() {
        informacionClimaViewModel =
            ViewModelProvider(this).get(InformacionClimaViewModel::class.java)
    }

    private fun verificarCiudadElegida() {
        val ciudadElegida = PreferenciasManager.obtenerInstancia()
            .obtener(ConstantesPreferencias.CIUDAD_BUSCADA_USUARIO, CiudadBuscada::class.java)
        if (ciudadElegida != null)
            consultarClimaCiudad(ciudadElegida.woeid.toString())
        else
            navegarSeleccionCiudad()
    }

    private fun navegarSeleccionCiudad() {
        val intentCiudad = Intent(this, ConsultaCiudadClimaActivity::class.java)
        startActivityForResult(intentCiudad, CodigoIntencion.CODIGO_INTENCION_OBTENER_CIUDAD)
    }


    private fun configurarToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun consultarClimaCiudad(idCiudad: String) {
        informacionClimaViewModel.obtenerClimaCiudad(idCiudad).observe(
            this, {
                llenarInformacionUI(it!!)
                llenarRecyclerView(it.consolidatedWeather)
            }
        )
    }

    private fun llenarRecyclerView(consolidatedWeather: List<ConsolidatedWeather>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adaptador = RecyclerViewClimaAdapter(consolidatedWeather, this)
        recyclerView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }

    private fun llenarInformacionUI(it: ClimaCiudad) {
        val climaConsolidado = it.consolidatedWeather[0]
        nombreCiudad.text = it.title
        tv_fecha_dipositivo.text = climaConsolidado.applicableDate
        tv_temperatura_ciudad.text = String.format("%s°", climaConsolidado.theTemp.toInt())
        tv_temperatura_max_min.text = String.format(
            " %s°  %s° ",
            climaConsolidado.minTemp.toInt(),
            climaConsolidado.maxTemp.toInt()
        )
        tv_estado_ciudad.text = climaConsolidado.weatherStateName
        tvvalortemperatura.text = String.format("%s°", climaConsolidado.theTemp.toInt())
        tv_cantidad_dias_clima.text =
            String.format("%s Dias reporte del clima", it.consolidatedWeather.size)
        tvvisibilidad.text = String.format("%s km", climaConsolidado.visibility.toInt())
        tvpresionaire.text = String.format("%s hPa", climaConsolidado.airPressure.toInt())
        tvvalorHumedad.text = String.format("%s", climaConsolidado.humidity)
        tvvalordireccionviento.text = String.format("%s Km/h", climaConsolidado.windSpeed.toInt())
        tvdirecconviento.text = climaConsolidado.windDirectionCompass
    }
    //endregion
}