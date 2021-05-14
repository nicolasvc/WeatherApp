package com.example.weatherapp.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.R
import com.example.weatherapp.ui.informacionclima.InformacionClimaActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.viewpager_weather.*
import me.relex.circleindicator.CircleIndicator3

class SplashActivity : AppCompatActivity() {

    private var nombreCiudades = mutableListOf<String>()

    //region Sobrecarga
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        //setContentView(R.layout.viewpager_weather)
       /*postList()
        viewPager2.adapter = ViewPageAdapter(nombreCiudades)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        circleIndicator.setViewPager(viewPager2)*/
        iniciarAplicacion()
    }
    //endregion
    private fun postList(){
        for (i:Int in 1..5){
            nombreCiudades.add("Ciudad $i")
        }
    }


    //region Propios
    private fun iniciarAplicacion() {
        imageViewSplash.alpha = 0f
        imageViewSplash.animate().setDuration(1500).alpha(1f).withEndAction {
            val intentInicio = Intent(this, InformacionClimaActivity::class.java)
            startActivity(intentInicio)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
    //endregion
}