package com.example.weatherapp.vista.InformacionClimaActivity.splashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.weatherapp.R

class SplashActivity : AppCompatActivity() {
    private lateinit var imagen: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        //imagen = findViewById(R.id.imageViewLogin)
        imagen.alpha = 0f
        imagen.animate().setDuration(1500).alpha(1f).withEndAction{
           // val intentInicio = Intent(this, LoginActivity::class.java)
            //startActivity(intentInicio)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}