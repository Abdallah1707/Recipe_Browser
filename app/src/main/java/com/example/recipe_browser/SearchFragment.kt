package com.example.recipe_browser

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_browser.model.mealApiServices
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvSearch)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val etSearch = view.findViewById<TextInputEditText>(R.id.etSearch)


        performSearch(recyclerView, "a")

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s?.toString()?.trim().orEmpty()


                searchJob?.cancel()

                searchJob = lifecycleScope.launch {
                    delay(400) // debounce
                    if (query.isEmpty()) {
                        performSearch(recyclerView, "a")
                    } else {
                        performSearchByName(recyclerView, query)
                    }
                }
            }
        })
    }

    private fun performSearch(recyclerView: RecyclerView, letter: String) {
        lifecycleScope.launch {
            try {
                val response = mealApiServices.getMealsByLetter(letter)
                val meals = response.meals ?: emptyList()
                recyclerView.adapter = MealAdapter(meals) { meal ->
                    // navigate to detail fragment
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun performSearchByName(recyclerView: RecyclerView, query: String) {
        lifecycleScope.launch {
            try {
                val response = mealApiServices.searchMeal(query)
                val meals = response.meals ?: emptyList()
                recyclerView.adapter = MealAdapter(meals) { meal ->
                    // navigate to detail fragment
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}