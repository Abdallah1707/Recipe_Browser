package com.example.recipe_browser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.database.AppDatabase
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.model.RecentMeal
import kotlinx.coroutines.launch

class RecentViewModel(application: Application) :
    AndroidViewModel(application) {

    private val dao =
        AppDatabase
            .getDatabase(application)
            .recentMealDao()

    val recentMeals: LiveData<List<RecentMeal>> =
        dao.getRecentMeals()

    fun addRecent(meal: Meal) {

        viewModelScope.launch {

            dao.insertRecent(
                RecentMeal(
                    idMeal = meal.idMeal,
                    strMeal = meal.strMeal,
                    strCategory = meal.strCategory,
                    strMealThumb = meal.strMealThumb
                )
            )
        }
    }
}