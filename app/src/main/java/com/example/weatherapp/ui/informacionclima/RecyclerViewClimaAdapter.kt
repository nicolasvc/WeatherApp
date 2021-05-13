package com.example.weatherapp.ui.informacionclima

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.entidades.climaciudad.ConsolidatedWeather
import com.example.weatherapp.transversales.constantes.ConstantesRetrofit
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class RecyclerViewClimaAdapter(
    private var listaClima: List<ConsolidatedWeather>,
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
        holder.tvdiaClima.text = diaClima.applicableDate
        holder.tvdiaSemana.text = obtenerDiaSemana(diaClima.applicableDate)
        holder.tvTipoClima.text = diaClima.weatherStateName
        holder.tvTemperaturaMaxima.text =
            String.format("%s°/%s°", diaClima.minTemp.toInt(), diaClima.maxTemp.toInt())
        val estadoClimaSufijo = diaClima.weatherStateAbbr
        if (estadoClimaSufijo.isNotEmpty()) {
            Picasso.with(context)
                .load(String.format(ConstantesRetrofit.API_FILES_URL, estadoClimaSufijo))
                .error(R.drawable.ic_cloud)
                .into(holder.iVTipoClima);
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun obtenerDiaSemana(fecha: String): String {
        val date: Date = SimpleDateFormat("yyyy-M-d").parse(fecha)
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(date)
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
