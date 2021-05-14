package com.example.weatherapp.ui.consultarclimaciudad

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.di.application.MyApp
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


class ConsultaCiudadClimaActivity : AppCompatActivity(),
    RecyclerViewCiudadesAdapter.IlistenerAdapter {


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
        iniciarInyector()
        setContentView(R.layout.activity_consulta_ciudad_clima)
        configurarToolbar()
        iniciarViewModel()
        consultarCiudades("s")
        iniciarListenerAutoComplete()
        iniciarRecyclerView()
        consultarCiudadesElegidas()
        iniciarTouchHelpeRecyclerView()
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

    //region Inyeccion
    private fun iniciarInyector() {
        val aplicacion = application as MyApp
        aplicacion.getComponent().inject(this)
    }
    //endregion


    //region Propios
    private fun iniciarTouchHelpeRecyclerView(){
        val touchHelper = ItemTouchHelper(itemTouchHelperCallback)
        touchHelper.attachToRecyclerView(recyclerCiudadesElegidas)
    }

    val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when(direction){
                    ItemTouchHelper.LEFT->{
                        eliminarCiudad(position)
                    }
                    ItemTouchHelper.RIGHT->{
                        eliminarCiudad(position)
                    }
                }
            }
    }

    private fun eliminarCiudad(posicionEliminada: Int){
        val ciudadAEliminar = listaCiudadesElegidas[posicionEliminada]
        ciudadesElegidaViewModel.eliminarClimaCiudad(ciudadAEliminar.idCiudad)
        ciudadesElegidaViewModel.deleteCiudadPorId(ciudadAEliminar.idCiudad)
        Snackbar.make(coordinatorContenedor,"Se elimino la ciudad ${ciudadAEliminar.nombreciudad}",Snackbar.LENGTH_LONG).show()
    }



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
            listaCiudadesElegidas.find { it.idCiudad == ciudadPreseleccionada.woeid.toString() }
        if (ciudadEnLista == null)
            almacenarCiudadPreferencias(posicionElegida)
        else {
            consultarCiudades("s")
            autoCompleteCiudad.setText("")
            mostrarToast("No se puede consultar la misma ciudad")
        }
    }



    private fun mostrarMensajeConfirmacion() {
        val dialogoCierre = AlertDialog.Builder(this)
        dialogoCierre.setMessage("¿Quieres borrar todas las ciudades?")
            .setTitle("Eliminar ciudades")
        dialogoCierre.setPositiveButton("Eliminar") { dialog, _ ->
            PreferenciasManager.obtenerInstancia().limpiar()
            ciudadesElegidaViewModel.eliminarAllClimasCiudades()
            ciudadesElegidaViewModel.deleteAllCiudades()
            dialog.dismiss()
        }
        dialogoCierre.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        dialogoCierre.create()
        dialogoCierre.show()

    }

    private fun almacenarCiudadPreferencias(posicionElegida: Int) {
        val ciudadElegida = listaActualConsultaCiudades[posicionElegida]
        PreferenciasManager.obtenerInstancia()
            .almacenar(ConstantesPreferencias.USUARIO_TIENE_CIUDADES, ciudadElegida.woeid)
        finalizarActividad(ciudadElegida.woeid.toString())
    }

    private fun finalizarActividad(woeid: String) {
        val intentCiudadElegida = Intent()
        intentCiudadElegida.putExtra(
            ConstantesCompartidas.LLAVE_ID_CIUDAD_INTENT,
            woeid
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
        listaCiudadesElegidas = LinkedList()
        recyclerCiudadesElegidas.layoutManager = LinearLayoutManager(this)
        adaptadorRecyclerView = RecyclerViewCiudadesAdapter(listaCiudadesElegidas, this, this)
        recyclerCiudadesElegidas.adapter = adaptadorRecyclerView
    }

    private fun iniciarViewModel() {
        informacionClimaViewModel =
            ViewModelProvider(this).get(InformacionClimaViewModel::class.java)
        ciudadesElegidaViewModel = ViewModelProvider(this).get(CiudadElegidaViewModel::class.java)
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun consultarCiudadesElegidas() {
        ciudadesElegidaViewModel.getAllCiudades().observe(this, {
            if (it.isEmpty()) {
                mostrarToast("Por favor ingresa una ciudad primero")
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    //endregion

    //region Callback Recyclerview
    override fun onClickCiudad(ciudadElegida: CiudadSeleccionadaEntity) {
        finalizarActividad(ciudadElegida.idCiudad)
    }

    //endregion


    //region ContratosPresentador

    //endregion


}