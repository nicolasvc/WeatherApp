package com.example.weatherapp.ui.consultarclimaciudad

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.entidades.busquedaciudad.CiudadBuscada
import com.example.weatherapp.origendatos.room.entidades.CiudadSeleccionadaEntity
import com.example.weatherapp.origendatos.viewmodel.CiudadElegidaViewModel
import com.example.weatherapp.origendatos.viewmodel.InformacionClimaViewModel
import com.example.weatherapp.transversales.constantes.ConstantesCompartidas
import com.example.weatherapp.transversales.constantes.ConstantesPreferencias
import com.example.weatherapp.transversales.preferencias.PreferenciasManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_consulta_ciudad_clima.*
import java.util.*


class ConsultaCiudadClimaActivity : AppCompatActivity() {


    //region Propiedades
    private lateinit var informacionClimaViewModel: InformacionClimaViewModel
    private lateinit var ciudadesElegidaViewModel: CiudadElegidaViewModel
    private lateinit var listaActualConsultaCiudades: List<CiudadBuscada>
    private lateinit var listaCiudadesElegidas: List<CiudadSeleccionadaEntity>
    private lateinit var adaptadorRecyclerView: RecyclerViewCiudadesAdapter
    //endregion

    //region Sobrecarga
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_ciudad_clima)
        configurarToolbar()
        iniciarViewModel()
        consultarCiudades("s")
        iniciarListenerAutoComplete()
        iniciarRecyclerView()
        consultarCiudadesElegidas()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.eliminarCiudad -> {
                mostrarMensajeConfirmacion()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_eliminacion_ciudades, menu)
        return true
    }

    //endregion
    //region Propios

    private fun iniciarListenerAutoComplete() {
        autoCompleteCiudad.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                consultarCiudades(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
        autoCompleteCiudad.setOnItemClickListener { _, _, position, _ ->
            validarCiudadUnica(position)
        }
    }

    private fun validarCiudadUnica(posicionElegida: Int) {
        val ciudadPreseleccionada = listaActualConsultaCiudades[posicionElegida]
        val ciudadEnLista: CiudadSeleccionadaEntity? =
            listaCiudadesElegidas.find { it.woeidCiudad == ciudadPreseleccionada.woeid.toString() }
        if (ciudadEnLista == null)
            finalizarActividadExitosamente(posicionElegida)
        else {
            consultarCiudades("s")
            autoCompleteCiudad.setText("")
            mostrarSnackBar("No se puede consultar la misma ciudad")


        }
    }

    private fun mostrarMensajeConfirmacion() {
        val dialogoCierre = AlertDialog.Builder(this)
        dialogoCierre.setMessage("¿Quieres borrar todas las ciudades?")
            .setTitle("Eliminar ciudades")
        dialogoCierre.setPositiveButton("Eliminar") { dialog, _ ->
            ciudadesElegidaViewModel.deleteAllCiudades()
            dialog.dismiss()
        }
        dialogoCierre.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        dialogoCierre.create()
        dialogoCierre.show()

    }

    private fun finalizarActividadExitosamente(posicionElegida: Int) {
        val ciudadElegida = listaActualConsultaCiudades[posicionElegida]
        PreferenciasManager.obtenerInstancia()
            .almacenar(ConstantesPreferencias.CIUDAD_BUSCADA_USUARIO, ciudadElegida)
        val intentCiudadElegida = Intent()
        intentCiudadElegida.putExtra(
            ConstantesCompartidas.LLAVE_ID_CIUDAD_INTENT,
            ciudadElegida.woeid.toString()
        )
        setResult(RESULT_OK, intentCiudadElegida)
        finish()
    }

    private fun consultarCiudades(ciudad: String) {
        informacionClimaViewModel.obtenerCiudades(ciudad).observe(this, {
            listaActualConsultaCiudades = it
            val nuevaListaCiudades = mutableListOf<String>()
            it.map { ciudadBuscada -> nuevaListaCiudades.add(ciudadBuscada.title + " / " + ciudadBuscada.locationType) }
            autoCompleteCiudad.setAdapter(
                ArrayAdapter(
                    this,
                    R.layout.support_simple_spinner_dropdown_item,
                    nuevaListaCiudades
                )
            )
        })
    }

    private fun iniciarRecyclerView() {
        recyclerCiudadesElegidas.layoutManager = LinearLayoutManager(this)
        adaptadorRecyclerView = RecyclerViewCiudadesAdapter(LinkedList(), this)
        recyclerCiudadesElegidas.adapter = adaptadorRecyclerView
    }

    private fun iniciarViewModel() {
        informacionClimaViewModel =
            ViewModelProvider(this).get(InformacionClimaViewModel::class.java)
        ciudadesElegidaViewModel = ViewModelProvider(this).get(CiudadElegidaViewModel::class.java)
    }

    private fun mostrarSnackBar(mensaje: String) {
        Snackbar.make(
            coordinatorContenedor,
            mensaje,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun consultarCiudadesElegidas() {
        ciudadesElegidaViewModel.getAllCiudades().observe(this, {
            if (it.isEmpty()) {
                mostrarSnackBar("Por favor ingresa una ciudad primero")
                mostrarCiudadesElegidas(LinkedList())
            } else
                mostrarCiudadesElegidas(it)
        })
    }

    private fun mostrarCiudadesElegidas(listCiudades: List<CiudadSeleccionadaEntity>?) {
        listaCiudadesElegidas = listCiudades!!
        adaptadorRecyclerView.setListaCiudades(listCiudades)
        adaptadorRecyclerView.notifyDataSetChanged()
    }


    private fun configurarToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //endregion


}