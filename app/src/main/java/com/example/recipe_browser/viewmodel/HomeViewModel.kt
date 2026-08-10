package com.example.recipe_browser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.model.Category
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.model.repository.RecipeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> = _meals

    private val _randomMeal = MutableLiveData<Meal?>()
    val randomMeal: LiveData<Meal?> = _randomMeal

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories


    fun loadMeals() {
        viewModelScope.launch {
            try {
                val meals = repository.searchMeal("a")

                _meals.value = meals

                repository.saveMealsToDatabase(meals)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun loadRandomMeal() {
        viewModelScope.launch {
            try {
                _randomMeal.value =
                    repository.getRandomMeal()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun loadCategories() {
        viewModelScope.launch {
            try {
                _categories.value =
                    repository.getCategories()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}