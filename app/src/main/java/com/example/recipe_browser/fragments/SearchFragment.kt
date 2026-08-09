package com.example.recipe_browser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_browser.R
import com.example.recipe_browser.model.MealAdapter
import com.example.recipe_browser.model.mealApiServices
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvSearch)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        view.findViewById<ImageView>(R.id.btnBack)?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        searchMeals("a")
        val etSearch = view.findViewById<TextInputEditText>(R.id.etSearch)

        etSearch.doOnTextChanged { text, _, _, _ ->
            val query = text.toString()
            if (query.isNotEmpty()) {
                searchMeals(query)
            }
        }
    }

    private fun searchMeals(query: String) {
        lifecycleScope.launch {
            try {
                val response = mealApiServices.searchMeal(query)
                val meals = response.meals ?: emptyList()

                val sortedMeals = meals.sortedBy { it.strMeal }

                recyclerView.adapter = MealAdapter(sortedMeals) { meal ->
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, DetailsFragment.newInstance(meal.idMeal))
                        .addToBackStack(null)
                        .commit()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
