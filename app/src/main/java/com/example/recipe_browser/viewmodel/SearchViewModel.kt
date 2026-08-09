package com.example.recipe_browser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.RecipeRepository
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.model.mealApiServices

import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = RecipeRepository(mealApiServices)

    val meals = MutableLiveData<List<Meal>>()

    fun search(name: String) {

        viewModelScope.launch {

            try {

                meals.value = repository.searchMeals(name)

            } catch (e: Exception) {

                meals.value = emptyList()

            }

        }

    }

}