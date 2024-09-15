package com.aliosman.filmanter

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aliosman.filmanter.databinding.ActivityMainBinding
import com.aliosman.filmanter.ui.MainPage

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
                2 -> actionToNextActivity()
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
        }, 300)
    }

    // Butona tıklandığında 3. tanıtım sayfaınsa git
    private fun thirdIntroduce() {
        val fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        val nextButton = binding.buttonNext

        // Butonun hareket animasyonu
        val animator = ObjectAnimator.ofFloat(nextButton, "translationY", -300f)
        animator.duration = 300
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()

        // Buton metninin animasyonu
        val translateAnimation = TranslateAnimation(0f, 0f, 0f, -300f)
        translateAnimation.duration = 300 // 300ms boyunca sürdür
        translateAnimation.interpolator = AccelerateDecelerateInterpolator()
        translateAnimation.fillAfter = true // Animasyon tamamlanınca bitir

        // Film resmini fadeout ile kaybet
        binding.mainCover.let {
            it.startAnimation(fadeOut)
            it.visibility = View.INVISIBLE
        }

        // Film örnek metnini fadeout ile kaybet
        binding.innerIntroduceText.let {
            it.startAnimation(fadeOut)
            it.visibility = View.INVISIBLE
        }

        // Başlama metnini fadeIn ile getir
        binding.textReadyToStart.let {
            it.startAnimation(fadeIn)
            it.visibility = View.VISIBLE
        }

        // Buton metnini fadeOut ile kaybet
        binding.txtNext.let {
            it.textSize = 16f
            it.startAnimation(translateAnimation)
            val start = getString(R.string.start)
            it.text = start
        }
    }

    private fun actionToNextActivity() {
        val action = Intent(this, MainPage::class.java)
        startActivity(action)
    }
}