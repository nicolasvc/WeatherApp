package com.example.weatherapp.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.R
import com.example.weatherapp.ui.informacionclima.InformacionClimaActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    //region Sobrecarga
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        iniciarAplicacion()
    }
    //endregion


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