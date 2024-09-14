package com.aliosman.filmanter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Toast
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

        // Tanıtım metnini 1000ms gecikmeli başlat
        Handler(Looper.getMainLooper()).postDelayed({
            val introText1 = getString(R.string.introduce1)
            introduceTextAnimation(introText1)
        }, 1000)

        // Butona tıklandığında sırasıyla animasyonları getir.
        var num = 0
        binding.buttonNext.setOnClickListener {
            binding.introduce.text = null
            when(num) {
                0 -> secondIntroduce()
                1 -> thirdIntroduce()
            }
            num += 1
        }
    }

    // Giriş metni için animasyon oluştur
    private fun introduceTextAnimation(msg: String) {
        val introduceText = binding.introduce
        introduceText.text = msg
        val animate = AnimationUtils.loadAnimation(this, R.anim.introduce_text_anim)
        introduceText.startAnimation(animate)
    }

    // Butona tıklandığında ikinci
    private fun secondIntroduce() {
        val coverMotions = binding.constraintLayout
        val introText2 = getString(R.string.introduce2)
        Handler(Looper.getMainLooper()).postDelayed({
            coverMotions.transitionToEnd()
        },  100)
        Handler().postDelayed({
            introduceTextAnimation(introText2)
        }, 400)
    }

    // Butona tıklandığında 3. tanıtım sayfaınsa git
    private fun thirdIntroduce() {
        Toast.makeText(this, "[Test]İkinci tıklama", Toast.LENGTH_SHORT).show()
    }
}