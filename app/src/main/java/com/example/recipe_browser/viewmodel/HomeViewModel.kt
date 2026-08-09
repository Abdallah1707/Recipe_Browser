package com.example.recipe_browser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.RecipeRepository
import com.example.recipe_browser.model.Category
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.model.mealApiServices

import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = RecipeRepository(mealApiServices)

    val meals = MutableLiveData<List<Meal>>()
    val categories = MutableLiveData<List<Category>>()

    fun loadMeals() {

        viewModelScope.launch {

            try {

                meals.value = repository.getPopularRecipes()

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
    val randomMeal = MutableLiveData<Meal>()

    fun loadRandomMeal() {
        viewModelScope.launch {
            try {
                randomMeal.value = repository.getRandomMeal()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}