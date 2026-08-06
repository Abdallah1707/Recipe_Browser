package com.example.recipe_browser.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe_browser.fragments.LoginFragment
import com.example.recipe_browser.fragments.SplashFragment

class AuthActivity : AppCompatActivity(), SplashFragment.SplashListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, SplashFragment())
                .commit()
        }
    }

    override fun onSplashFinished(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, LoginFragment())
                .commit()
        }
    }
}