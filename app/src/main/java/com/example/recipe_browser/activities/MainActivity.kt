package com.example.recipe_browser.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.recipe_browser.R
import com.example.recipe_browser.fragments.AboutFragment
import com.example.recipe_browser.fragments.FavoriteFragment
import com.example.recipe_browser.fragments.HomeFragment
import com.example.recipe_browser.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigation)

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.homeFragment -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.searchFragment -> {
                    replaceFragment(SearchFragment())
                    true
                }

                R.id.favoriteFragment -> {
                    replaceFragment(FavoriteFragment())
                    true
                }

                R.id.aboutFragment -> {
                    replaceFragment(AboutFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}