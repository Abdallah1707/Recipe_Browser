package com.example.recipe_browser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.model.Category
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.repository.MealRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = MealRepository()

    val meals = MutableLiveData<List<Meal>>()
    val categories = MutableLiveData<List<Category>>()

    fun loadMeals() {

        viewModelScope.launch {

            try {

                meals.value = repository.getMeals()

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    fun loadCategories() {

        viewModelScope.launch {

            try {

                categories.value = repository.getCategories()

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }
}