package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.recipe_browser.R
import com.example.recipe_browser.room.MealDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val imgProfileToolbar = view.findViewById<ImageView>(R.id.imgProfileToolbar)
        imgProfileToolbar.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(android.R.id.content, UserFragment())
                .addToBackStack(null)
                .commit()
        }

        val etSearch = view.findViewById<TextInputEditText>(R.id.etSearch)
        etSearch.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(android.R.id.content, SearchFragment())
                .addToBackStack(null)
                .commit()
        }
        etSearch.isFocusable = false

        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> true
                R.id.searchFragment -> {
                    parentFragmentManager.beginTransaction()
                        .replace(android.R.id.content, SearchFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.favoriteFragment -> {
                    parentFragmentManager.beginTransaction()
                        .replace(android.R.id.content, FavoriteFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.aboutFragment -> {
                    parentFragmentManager.beginTransaction()
                        .replace(android.R.id.content, AboutFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtHello = view.findViewById<TextView>(R.id.txtHello)
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("userEmail", null)

        if (userEmail != null) {
            val db = MealDatabase.getDatabase(requireContext())
            lifecycleScope.launch {
                try {
                    val user = db.userDao().getUserByEmail(userEmail)
                    if (user != null) {
                        txtHello.text = "Hello, ${user.name} 👋"
                    }
                } catch (e: Exception) {
                    // Fail silently
                }
            }
        }
    }
}
