package com.example.onlineshopping.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlineshopping.R
import com.example.onlineshopping.databinding.ActivityMainBinding
import com.example.onlineshopping.databinding.ActivitySplashBinding
import com.example.onlineshopping.screen.profil.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.animationView.postDelayed({
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }, 3000)
    }
}