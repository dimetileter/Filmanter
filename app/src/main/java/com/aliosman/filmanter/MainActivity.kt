package com.aliosman.filmanter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aliosman.filmanter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val motionLayout = binding.main
        Handler(Looper.getMainLooper()).postDelayed({
            motionLayout.transitionToEnd()
        },  1000)

        val coverMotions = binding.constraintLayout
        binding.buttonNext.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                coverMotions.transitionToEnd()
            },  100)
        }

    }
}