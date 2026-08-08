package com.example.recipe_browser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.repository.MealRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = MealRepository()

    val meals = MutableLiveData<List<Meal>>()

    fun search(name: String) {

        viewModelScope.launch {

            try {
                meals.value = repository.searchMeals(name)
            } catch (e: Exception) {
                e.printStackTrace()
                meals.value = emptyList()
            }
        }
    }

    fun searchByCategory(category: String) {

        viewModelScope.launch {

            try {
                meals.value = repository.getMealsByCategory(category)
            } catch (e: Exception) {
                e.printStackTrace()
                meals.value = emptyList()
            }
        }
    }
}