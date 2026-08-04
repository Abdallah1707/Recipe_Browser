package com.example.recipe_browser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etSearch = view.findViewById<TextInputEditText>(R.id.etSearch)
        etSearch.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment())
                .addToBackStack(null)
                .commit()
        }

        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {

                    true
                }
                R.id.searchFragment -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SearchFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.favoriteFragment -> {
                    // parentFragmentManager.beginTransaction()
                    //     .replace(R.id.fragment_container, FavoriteFragment())
                    //     .addToBackStack(null)
                    //     .commit()
                    true
                }
                R.id.aboutFragment -> {
                     parentFragmentManager.beginTransaction()
                         .replace(R.id.fragment_container, AboutFragment())
                         .addToBackStack(null)
                         .commit()
                    true
                }
                else -> false
            }
        }
    }
}