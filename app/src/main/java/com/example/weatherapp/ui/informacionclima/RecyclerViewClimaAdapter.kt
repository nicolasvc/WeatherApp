package com.example.weatherapp.ui.informacionclima

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.origendatos.room.entidades.ClimaCiudadDiaEntity
import com.example.weatherapp.transversales.utilidades.FechasHelper
import com.example.weatherapp.transversales.utilidades.ObtenerRecursosHelper


class RecyclerViewClimaAdapter(
    private var listaClima: List<ClimaCiudadDiaEntity>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerViewClimaAdapter.ViewHolder>() {


    //region Sobrecarga
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_dia_clima, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listaClima.isEmpty()) return
        val diaClima = listaClima[position]
        holder.tvdiaClima.text = FechasHelper.obtenerInstancia().obtenerMes(diaClima.applicableDate)
        holder.tvdiaSemana.text = FechasHelper.obtenerInstancia().obtenerDiaSemana(diaClima.applicableDate)
        holder.tvTipoClima.text = diaClima.weatherStateName
        holder.tvTemperaturaMaxima.text = String.format("%s°/%s°", diaClima.minTemp.toInt(), diaClima.maxTemp.toInt())
        holder.iVTipoClima.setImageResource(ObtenerRecursosHelper.obtenerInstancia().obtenerDrawableIconosClima(diaClima.weatherStateAbbr))
        }




    override fun getItemCount(): Int = listaClima.size
    //endregion


    //region ViewHolder
    inner class ViewHolder(view: View?) :
        RecyclerView.ViewHolder(view!!) {
        val tvdiaClima: TextView = view!!.findViewById(R.id.tvdiaclima)
        val tvdiaSemana: TextView = view!!.findViewById(R.id.tvdiasemanaclima)
        val tvTipoClima: TextView = view!!.findViewById(R.id.tvtipoclima)
        val tvTemperaturaMaxima: TextView = view!!.findViewById(R.id.temperaturalmaxmin)
        val iVTipoClima: ImageView = view!!.findViewById(R.id.ivestadoclima)

        override fun toString(): String {
            return super.toString() + " '"
        }
    }
    //endregion




}
