package com.example.finproject;

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenLogo : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen_logo)

        lifecycleScope.launch {
            delay(SPLASH_TIME_OUT)
            startActivity(Intent(this@SplashScreenLogo, splashScreen::class.java))
            finish()
        }
    }
}
