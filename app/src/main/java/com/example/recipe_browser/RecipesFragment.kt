package com.example.recipe_browser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.model.mealApiServices
import kotlinx.coroutines.launch

class RecipesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_meal)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            val allMeals = mutableListOf<Meal>()

            for (letter in 'a'..'z') {
                try {
                    val response = mealApiServices.getMealsByLetter(letter.toString())
                    response.meals?.let { allMeals.addAll(it) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            recyclerView.adapter = MealAdapter(allMeals) { meal -> R.layout.fragment_recipe_detail
            }
        }
    }
}