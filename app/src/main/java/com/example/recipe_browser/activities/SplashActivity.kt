package com.example.recipe_browser.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe_browser.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.imgLogo)
        val title = findViewById<TextView>(R.id.txtTitle)
        val sub = findViewById<TextView>(R.id.txtSub)

        val dot1 = findViewById<View>(R.id.dot1)
        val dot2 = findViewById<View>(R.id.dot2)
        val dot3 = findViewById<View>(R.id.dot3)

        // Logo Animation
        logo.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.logo_anim)
        )

        // Text Animation
//        title.startAnimation(
//            AnimationUtils.loadAnimation(this, R.anim.text_anim)
//        )
//
//        sub.startAnimation(
//            AnimationUtils.loadAnimation(this, R.anim.text_anim)
//        )

        // Dots Animation
        val dotsAnim = AnimationUtils.loadAnimation(this, R.anim.dots_anim)

        dot1.startAnimation(dotsAnim)

        Handler(Looper.getMainLooper()).postDelayed({
            dot2.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.dots_anim)
            )
        }, 200)

        Handler(Looper.getMainLooper()).postDelayed({
            dot3.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.dots_anim)
            )
        }, 400)

        // Go To Login
        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()

        }, 3000)
    }
}