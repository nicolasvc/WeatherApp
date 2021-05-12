package com.example.weatherapp.vista.consultarclimaciudad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.entidades.busquedaciudad.CiudadElegida
import com.google.android.flexbox.FlexboxLayout

class RecyclerViewCiudades(
    private var listaCiudades: List<CiudadElegida>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerViewCiudades.ViewHolder>() {


    //region Sobrecarga
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_ciudades_elegidas, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listaCiudades.isEmpty()) return

        val ciudad = listaCiudades[position]
        holder.tv_temperatura_ciudad.text =
            String.format("%s °C", ciudad.temperaturaCiudad.toString())
        holder.tv_ciudad_elegida.text = ciudad.title
        holder.tv_tipo_clima.text = ciudad.estadoClima
        holder.flexboxLayout.setBackgroundColor(context.resources.getColor(R.color.colorclimadia))
    }


    override fun getItemCount(): Int = listaCiudades.size
    //endregion


    //region ViewHolder
    inner class ViewHolder(view: View?) :
        RecyclerView.ViewHolder(view!!) {
        val tv_temperatura_ciudad: TextView = view!!.findViewById(R.id.tv_temperatura_ciudad_item)
        val tv_ciudad_elegida: TextView = view!!.findViewById(R.id.tv_nombre_ciudad_elegida)
        val tv_tipo_clima: TextView = view!!.findViewById(R.id.tv_tipo_clima)
        val flexboxLayout: FlexboxLayout = view!!.findViewById(R.id.flexBoxcontenedor)
        override fun toString(): String {
            return super.toString() + " '"
        }
    }
    //endregion


}