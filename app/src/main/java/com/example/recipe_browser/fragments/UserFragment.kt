package com.example.recipe_browser.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.recipe_browser.R
import com.example.recipe_browser.activities.AuthActivity
import com.example.recipe_browser.room.MealDatabase
import kotlinx.coroutines.launch

class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val tvPhone = view.findViewById<TextView>(R.id.tvPhone)
        val tvAge = view.findViewById<TextView>(R.id.tvAge)
        val tvCountry = view.findViewById<TextView>(R.id.tvCountry)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userEmail = sharedPref.getString("userEmail", null)

        if (userEmail != null) {
            val db = MealDatabase.getDatabase(requireContext())
            lifecycleScope.launch {
                try {
                    val user = db.userDao().getUserByEmail(userEmail)
                    if (user != null) {
                        tvName.text = user.name
                        tvEmail.text = user.email
                        tvPhone.text = user.phone
                        tvAge.text = user.age
                        tvCountry.text = user.country
                    } else {
                        // User not found in DB (migration might have wiped it)
                        performLogout(sharedPref)
                    }
                } catch (e: Exception) {
                    // Database error, safe logout to reset state
                    performLogout(sharedPref)
                }
            }
        }

        btnLogout.setOnClickListener {
            performLogout(sharedPref)
        }
    }

    private fun performLogout(sharedPref: android.content.SharedPreferences) {
        sharedPref.edit().putBoolean("isLoggedIn", false).apply()
        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}