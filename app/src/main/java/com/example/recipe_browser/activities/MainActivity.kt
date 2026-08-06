package com.example.recipe_browser.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe_browser.R
import com.example.recipe_browser.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, HomeFragment())
                .commit()
        }
    }
}