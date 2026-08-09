package com.example.recipe_browser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.RecipeRepository
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.model.mealApiServices

import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val repository = RecipeRepository(mealApiServices)

    val meal = MutableLiveData<Meal>()

    fun loadMeal(id: String) {

        viewModelScope.launch {

            meal.value = repository.getMealDetails(id)

        }

    }
}