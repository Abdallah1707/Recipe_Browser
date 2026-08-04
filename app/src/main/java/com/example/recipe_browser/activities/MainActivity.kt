package com.example.recipe_browser.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.example.recipe_browser.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val welcomeText = findViewById<TextView>(R.id.welcome_text)
        val btnLogout = findViewById<Button>(R.id.btn_logout)

        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("userEmail", "User")

        welcomeText.text = "Welcome, $userEmail!"
        btnLogout.setOnClickListener {
            sharedPref.edit().putBoolean("isLoggedIn", false).apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}