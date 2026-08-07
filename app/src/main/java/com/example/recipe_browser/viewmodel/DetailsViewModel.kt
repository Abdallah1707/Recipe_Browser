package com.example.recipe_browser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.repository.MealRepository
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val repository = MealRepository()

    val meal = MutableLiveData<Meal>()

    fun loadMeal(id: String) {

        viewModelScope.launch {

            meal.value = repository.getMealDetails(id)

        }

    }
}