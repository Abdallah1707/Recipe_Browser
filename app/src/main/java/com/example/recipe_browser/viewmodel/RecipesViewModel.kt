package com.example.recipe_browser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.model.mealApiServices
import kotlinx.coroutines.launch

class RecipesViewModel : ViewModel() {

    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> get() = _meals

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchAllRecipes() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            val allMeals = mutableListOf<Meal>()
            try {
                // Fetching by letters a to z to get a good list of recipes
                for (letter in 'a'..'z') {
                    val response = mealApiServices.getMealsByLetter(letter.toString())
                    response.meals?.let { allMeals.addAll(it) }
                }
                _meals.value = allMeals
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
