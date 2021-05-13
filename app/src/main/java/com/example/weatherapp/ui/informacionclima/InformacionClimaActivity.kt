package com.example.weatherapp.ui.informacionclima

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.entidades.busquedaciudad.CiudadBuscada
import com.example.weatherapp.entidades.climaciudad.ClimaCiudad
import com.example.weatherapp.entidades.climaciudad.ConsolidatedWeather
import com.example.weatherapp.origendatos.room.entidades.CiudadSeleccionadaEntity
import com.example.weatherapp.origendatos.viewmodel.CiudadElegidaViewModel
import com.example.weatherapp.origendatos.viewmodel.InformacionClimaViewModel
import com.example.weatherapp.transversales.constantes.ConstantesCompartidas
import com.example.weatherapp.transversales.constantes.ConstantesPreferencias
import com.example.weatherapp.transversales.preferencias.PreferenciasManager
import com.example.weatherapp.ui.consultarclimaciudad.ConsultaCiudadClimaActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.grid_layout_detalle_clima.*
import kotlinx.android.synthetic.main.toolbar_base.*


class InformacionClimaActivity : AppCompatActivity() {

    //region Propiedades
    private lateinit var informacionClimaViewModel: InformacionClimaViewModel
    private lateinit var ciudadesElegidaViewModel: CiudadElegidaViewModel
    private var isNuevaCiudad:Boolean = false
    //endregion


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
    //endregion


    //region Metodos propios
    private fun iniciarViewModel() {
        informacionClimaViewModel =
            ViewModelProvider(this).get(InformacionClimaViewModel::class.java)
        ciudadesElegidaViewModel = ViewModelProvider(this).get(CiudadElegidaViewModel::class.java)
    }

    private fun verificarCiudadElegida() {
        val ciudadElegida = PreferenciasManager.obtenerInstancia()
            .obtener(ConstantesPreferencias.CIUDAD_BUSCADA_USUARIO, CiudadBuscada::class.java)
        if (ciudadElegida != null)
            consultarClimaCiudad(ciudadElegida.woeid.toString())
        else
            navegarSeleccionCiudad()
    }


    private var resultadoActividad =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val idCiudad = data!!.getStringExtra(ConstantesCompartidas.LLAVE_ID_CIUDAD_INTENT)
                consultarClimaCiudad(idCiudad!!)
            }
        }


    private fun navegarSeleccionCiudad() {
        val intent = Intent(this, ConsultaCiudadClimaActivity::class.java)
        resultadoActividad.launch(intent)
    }


    private fun configurarToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun consultarClimaCiudad(idCiudad: String) {
        informacionClimaViewModel.obtenerClimaCiudad(idCiudad).observe(
            this, { climaCiudad ->
                almacenarCiudad(climaCiudad)
                llenarInformacionUI(climaCiudad!!)
                llenarRecyclerView(climaCiudad.consolidatedWeather)
            }
        )
    }

    private fun almacenarCiudad(climaCiudad: ClimaCiudad){
        if(isNuevaCiudad){
            val ciudadEntity = CiudadSeleccionadaEntity(
                climaCiudad.consolidatedWeather[0].theTemp.toInt(),
                climaCiudad.consolidatedWeather[0].weatherStateName,
                climaCiudad.woeid.toString(),
                climaCiudad.title
            )
            ciudadesElegidaViewModel.insertCiudad(ciudadEntity)
        }

    }


    private fun llenarRecyclerView(consolidatedWeather: List<ConsolidatedWeather>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adaptador = RecyclerViewClimaAdapter(consolidatedWeather, this)
        recyclerView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }

    private fun llenarInformacionUI(climaCiudad: ClimaCiudad) {
        val climaConsolidado = climaCiudad.consolidatedWeather[0]
        nombreCiudad.text = climaCiudad.title
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
            String.format("%s Dias reporte del clima", climaCiudad.consolidatedWeather.size)
        tvvisibilidad.text = String.format("%s km", climaConsolidado.visibility.toInt())
        tvpresionaire.text = String.format("%s hPa", climaConsolidado.airPressure.toInt())
        tvvalorHumedad.text = String.format("%s", climaConsolidado.humidity)
        tvvalordireccionviento.text = String.format("%s Km/h", climaConsolidado.windSpeed.toInt())
        tvdirecconviento.text = climaConsolidado.windDirectionCompass
    }
    //endregion
}