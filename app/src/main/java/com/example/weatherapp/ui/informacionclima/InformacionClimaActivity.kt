package com.example.weatherapp.ui.informacionclima

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.R
import com.example.weatherapp.entidades.climaciudad.ClimaCiudad
import com.example.weatherapp.entidades.climaciudad.ConsolidatedWeather
import com.example.weatherapp.origendatos.room.entidades.CiudadConClimaDataClass
import com.example.weatherapp.origendatos.room.entidades.CiudadSeleccionadaEntity
import com.example.weatherapp.origendatos.room.entidades.ClimaCiudadDiaEntity
import com.example.weatherapp.origendatos.viewmodel.CiudadElegidaViewModel
import com.example.weatherapp.origendatos.viewmodel.InformacionClimaViewModel
import com.example.weatherapp.transversales.constantes.ConstantesCompartidas
import com.example.weatherapp.transversales.constantes.ConstantesPreferencias
import com.example.weatherapp.transversales.preferencias.PreferenciasManager
import com.example.weatherapp.ui.consultarclimaciudad.ConsultaCiudadClimaActivity
import kotlinx.android.synthetic.main.viewpager_weather.*
import java.util.*


class InformacionClimaActivity : AppCompatActivity(), ViewPageAdapter.IcallbackSweep {

    //region Propiedades
    private lateinit var informacionClimaViewModel: InformacionClimaViewModel
    private lateinit var ciudadesElegidaViewModel: CiudadElegidaViewModel
    private lateinit var viewPageAdapter: ViewPageAdapter
    private var isCiudadNueva: Boolean = false
    private lateinit var listaCiudadComplete: List<CiudadConClimaDataClass>
    //endregion


    //region Sobrecarga
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpager_weather)
        iniciarViewPager()
        iniciarViewModel()
        validarCiudadesConsultadas()
        consultarCiudadesNuevas()
        supportActionBar?.setBackgroundDrawable(applicationContext.getDrawable(R.drawable.backgroundtransparente))
    }

    private fun validarCiudadesConsultadas() {
        if (PreferenciasManager.obtenerInstancia()
                .obtener(ConstantesPreferencias.USUARIO_TIENE_CIUDADES, String()::class.java)
                .isNullOrEmpty()
        )
            navegarSeleccionCiudad()
    }

    private fun consultarCiudadesNuevas() {
        ciudadesElegidaViewModel.getAllCiudadesClima().observe(this, {
            validarCiudadesNuevas(it)
        })
    }


    private fun validarCiudadesNuevas(listaCiudadConClima: List<CiudadConClimaDataClass>?) {
        listaCiudadComplete = listaCiudadConClima!!
        viewPageAdapter.setListaCiudades(listaCiudadConClima)
        viewPageAdapter.notifyDataSetChanged()
        if (isCiudadNueva)
            viewPager2.setCurrentItem(listaCiudadConClima.size, false)
    }


    private fun iniciarViewPager() {
        listaCiudadComplete = LinkedList()
        viewPageAdapter = ViewPageAdapter(listaCiudadComplete, this, this)
        viewPager2.adapter = viewPageAdapter
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
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


    private var resultadoActividad =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val idCiudad = data!!.getStringExtra(ConstantesCompartidas.LLAVE_ID_CIUDAD_INTENT)
                isCiudadNueva = true
                PreferenciasManager.obtenerInstancia()
                    .almacenar(ConstantesPreferencias.USUARIO_TIENE_CIUDADES, true)
                consultarClimaCiudad(idCiudad!!)
            }
        }


    private fun navegarSeleccionCiudad() {
        val intent = Intent(this, ConsultaCiudadClimaActivity::class.java)
        resultadoActividad.launch(intent)
    }


    private fun consultarClimaCiudad(idCiudad: String) {
        val indiceCiudadConsultada =
            listaCiudadComplete.indexOfFirst { ciudadConClimaDataClass -> ciudadConClimaDataClass.ciudad.idCiudad == idCiudad }
        if (indiceCiudadConsultada != -1) {
            viewPager2.setCurrentItem(indiceCiudadConsultada, false)
            return
        }
        layoutProgress.visibility = View.VISIBLE
        informacionClimaViewModel.obtenerClimaCiudad(idCiudad).observe(
            this, { climaCiudad ->
                almacenarCiudad(climaCiudad)
                almacenarClimaCiudad(climaCiudad.consolidatedWeather, climaCiudad.woeid.toString())
                layoutProgress.visibility = View.GONE
            }
        )
    }

    private fun almacenarClimaCiudad(
        consolidatedWeather: List<ConsolidatedWeather>,
        woidCiudad: String
    ) {
        consolidatedWeather.forEach { clima ->
            ciudadesElegidaViewModel.insertClimaCiudad(
                obtenerClimaCiudad(clima, woidCiudad)
            )
        }
    }

    private fun obtenerClimaCiudad(
        consolidatedWeather: ConsolidatedWeather,
        woidCiudad: String
    ): ClimaCiudadDiaEntity {
        return ClimaCiudadDiaEntity(
            consolidatedWeather.airPressure,
            consolidatedWeather.applicableDate,
            consolidatedWeather.created,
            consolidatedWeather.humidity,
            consolidatedWeather.maxTemp,
            consolidatedWeather.minTemp,
            consolidatedWeather.predictability,
            consolidatedWeather.theTemp,
            consolidatedWeather.visibility,
            consolidatedWeather.weatherStateAbbr,
            consolidatedWeather.weatherStateName,
            consolidatedWeather.windDirection,
            consolidatedWeather.windSpeed,
            consolidatedWeather.windDirectionCompass,
            woidCiudad
        )
    }

    private fun almacenarCiudad(climaCiudad: ClimaCiudad) {
        ciudadesElegidaViewModel.insertCiudad(obtenerciudadEntity(climaCiudad))
    }

    private fun obtenerciudadEntity(climaCiudad: ClimaCiudad): CiudadSeleccionadaEntity {
        return CiudadSeleccionadaEntity(
            climaCiudad.consolidatedWeather[0].theTemp.toInt(),
            climaCiudad.consolidatedWeather[0].weatherStateName,
            climaCiudad.woeid.toString(),
            climaCiudad.title,
            climaCiudad.consolidatedWeather[0].weatherStateAbbr
        )
    }


    //endregion

    //region Callback ViewPageAdapter
    override fun consultarCiudad(idCiudad: String) {
        informacionClimaViewModel.obtenerClimaCiudad(idCiudad).observe(
            this, { climaCiudad ->
                ciudadesElegidaViewModel.updateCiudad(obtenerciudadEntity(climaCiudad))
                //almacenarClimaCiudad(climaCiudad.consolidatedWeather, climaCiudad.woeid.toString())
            }
        )
    }
    //endregion
}