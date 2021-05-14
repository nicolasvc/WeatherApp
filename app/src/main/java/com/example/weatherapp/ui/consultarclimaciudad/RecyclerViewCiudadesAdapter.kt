package com.example.weatherapp.ui.consultarclimaciudad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.origendatos.room.entidades.CiudadSeleccionadaEntity
import com.example.weatherapp.transversales.utilidades.ObtenerRecursosHelper
import com.google.android.flexbox.FlexboxLayout

class RecyclerViewCiudadesAdapter(
    private var listaCiudades: List<CiudadSeleccionadaEntity>,
    private val context: Context,
    private val listener: IlistenerAdapter
) : RecyclerView.Adapter<RecyclerViewCiudadesAdapter.ViewHolder>() {


    //region Sobrecarga
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_ciudades_elegidas, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listaCiudades.isEmpty()) return
        val ciudad = listaCiudades[position]
        holder.tvtemperaturaciudad.text =
            String.format("%s °C", ciudad.temperaturaCiudad.toString())
        holder.tvciudadelegida.text = ciudad.nombreciudad
        holder.tvtipoclima.text = ciudad.estadoClima
        holder.flexboxLayout.setBackgroundColor(ObtenerRecursosHelper.obtenerInstancia().obtenerDrawableBackgroundClima(ciudad.abreviaturaClima))
        holder.cardciudadelegida.setOnClickListener {
            obtenerCiudadElegida(position)
        }
    }

    override fun getItemCount(): Int = listaCiudades.size
    //endregion


    //region ViewHolder
    inner class ViewHolder(view: View?) :
        RecyclerView.ViewHolder(view!!) {
        val tvtemperaturaciudad: TextView = view!!.findViewById(R.id.tv_temperatura_ciudad_item)
        val tvciudadelegida: TextView = view!!.findViewById(R.id.tv_nombre_ciudad_elegida)
        val tvtipoclima: TextView = view!!.findViewById(R.id.tv_tipo_clima)
        val cardciudadelegida: CardView = view!!.findViewById(R.id.cardciudadelegida)
        val flexboxLayout :FlexboxLayout = view!!.findViewById(R.id.flexBoxcontenedor)
        override fun toString(): String {
            return super.toString() + " '"
        }
    }

    fun setListaCiudades(listaCiudades: List<CiudadSeleccionadaEntity>) {
        this.listaCiudades = listaCiudades
    }
    //endregion

    private fun obtenerCiudadElegida(position: Int) {
        listener.onClickCiudad(listaCiudades[position])
    }


    //region Listener
    interface IlistenerAdapter {
        fun onClickCiudad(ciudadElegida: CiudadSeleccionadaEntity)
    }
    //endregion


}