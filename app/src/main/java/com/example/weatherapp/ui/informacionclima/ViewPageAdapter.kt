package com.example.weatherapp.ui.informacionclima

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherapp.R
import com.example.weatherapp.origendatos.room.entidades.CiudadConClimaDataClass
import com.example.weatherapp.transversales.utilidades.FechasHelper
import com.example.weatherapp.transversales.utilidades.ObtenerRecursosHelper

class ViewPageAdapter(
    private var listaCiudadComplete: List<CiudadConClimaDataClass>,
    private val context: Context,
    private val callbackSweep :IcallbackSweep
) :
    RecyclerView.Adapter<ViewPageAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreCiudad: TextView = itemView.findViewById(R.id.nombreCiudad)
        val tvFechaDispositivo: TextView = itemView.findViewById(R.id.tv_fecha_dipositivo)
        val tvTemperaturaCiudad: TextView = itemView.findViewById(R.id.tv_temperatura_ciudad)
        val tvTemperaturaMax: TextView = itemView.findViewById(R.id.tv_temperatura_max_min)
        val tvestadoCiudad: TextView = itemView.findViewById(R.id.tv_estado_ciudad)
        val tvValorTemperatura: TextView = itemView.findViewById(R.id.tvvalortemperatura)
        val tv_cantidadDiasClima: TextView = itemView.findViewById(R.id.tv_cantidad_dias_clima)
        val tvVisibilidad: TextView = itemView.findViewById(R.id.tvvisibilidad)
        val tvpresionAire: TextView = itemView.findViewById(R.id.tvpresionaire)
        val tvValorHumedad: TextView = itemView.findViewById(R.id.tvvalorHumedad)
        val tvValorDireccionViento: TextView = itemView.findViewById(R.id.tvvalordireccionviento)
        val tvDireccionViento: TextView = itemView.findViewById(R.id.tvdirecconviento)
        val recyclerViewClima: RecyclerView = itemView.findViewById(R.id.recyclerView)
        val coordinatorLayout: ConstraintLayout =
            itemView.findViewById(R.id.constrainLayoutContenedorClima)
        val imagenIcono: ImageView = itemView.findViewById(R.id.imageView)
        val sweepRefresh : SwipeRefreshLayout = itemView.findViewById(R.id.sweepclimaCiudad)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_clima_ciudad, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val informacionCiudad = listaCiudadComplete[position]
        val listaClimaHoy = informacionCiudad.listaClimaCiudadEntity[0]
        holder.sweepRefresh.setOnRefreshListener{
            callbackSweep.consultarCiudad(listaClimaHoy.woeidCiudad)
        }
        holder.imagenIcono.setImageResource(
            ObtenerRecursosHelper.obtenerInstancia()
                .obtenerDrawableIconosClima(listaClimaHoy.weatherStateAbbr)
        )
        holder.coordinatorLayout.background = context.getDrawable(
            ObtenerRecursosHelper.obtenerInstancia()
                .obtenerDrawableBackgroundClima(listaClimaHoy.weatherStateAbbr)
        )
        holder.tvNombreCiudad.text = informacionCiudad.ciudad.nombreciudad
        holder.tvFechaDispositivo.text =
            FechasHelper.obtenerInstancia().obtenerFechaConsulta(listaClimaHoy.applicableDate)
        holder.tvTemperaturaCiudad.text = String.format("%s°", listaClimaHoy.theTemp.toInt())
        holder.tvTemperaturaMax.text = String.format(
            " %s°  %s° ",
            listaClimaHoy.maxTemp.toInt(),
            listaClimaHoy.minTemp.toInt()
        )
        holder.tvestadoCiudad.text = listaClimaHoy.weatherStateName
        holder.tvValorTemperatura.text = String.format("%s°", listaClimaHoy.theTemp.toInt())
        holder.tv_cantidadDiasClima.text = String.format(
            "%s Dias reporte del clima",
            informacionCiudad.listaClimaCiudadEntity.size
        )
        holder.tvVisibilidad.text = String.format("%s km", listaClimaHoy.visibility.toInt())
        holder.tvpresionAire.text = String.format("%s hPa", listaClimaHoy.airPressure.toInt())
        holder.tvValorHumedad.text = "${listaClimaHoy.humidity} %"
        holder.tvValorDireccionViento.text =
            String.format("%s Km/h", listaClimaHoy.windSpeed.toInt())
        holder.tvDireccionViento.text = listaClimaHoy.windDirectionCompass

        holder.recyclerViewClima.layoutManager = LinearLayoutManager(context)
        val adaptador = RecyclerViewClimaAdapter(informacionCiudad.listaClimaCiudadEntity, context)
        holder.recyclerViewClima.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }


    fun setListaCiudades(listaCiudades: List<CiudadConClimaDataClass>) {
        listaCiudadComplete = listaCiudades
    }

    override fun getItemCount(): Int = listaCiudadComplete.size


    interface IcallbackSweep{
        fun consultarCiudad(idCiudad:String)
    }

}