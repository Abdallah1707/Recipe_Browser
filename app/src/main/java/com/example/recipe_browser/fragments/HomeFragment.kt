package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.recipe_browser.R

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

        return view
    }
}