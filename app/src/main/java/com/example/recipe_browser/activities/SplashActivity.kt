package com.example.recipe_browser.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe_browser.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.imgLogo)

        // Find dots with safety (IDs might be different in XML)
        val dot1 = findViewById<View>(R.id.dot1)
        val dot2 = findViewById<View>(R.id.dot2)
        val dot3 = findViewById<View>(R.id.dot3)

        // Logo Animation
        logo?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.logo_anim))

        // Dots Animation
        val dotsAnim = AnimationUtils.loadAnimation(this, R.anim.dots_anim)
        dot1?.startAnimation(dotsAnim)

        Handler(Looper.getMainLooper()).postDelayed({
            dot2?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.dots_anim))
        }, 200)

        Handler(Looper.getMainLooper()).postDelayed({
            dot3?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.dots_anim))
        }, 400)

        // Check Login Status after delay
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

            if (isLoggedIn) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 3000)
    }
}