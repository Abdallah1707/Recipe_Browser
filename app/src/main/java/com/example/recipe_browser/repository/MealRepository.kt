package com.example.recipe_browser.repository

import com.example.recipe_browser.model.Category
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.network.RetrofitClient

class MealRepository {

    suspend fun getMeals(): List<Meal> {
        return RetrofitClient.api.searchMeal("a").meals ?: emptyList()
    }

    suspend fun getMealDetails(id: String): Meal {
        return RetrofitClient.api.lookupMeal(id).meals!!.first()
    }

    suspend fun getCategories(): List<Category> {
        return RetrofitClient.api.getCategories().categories
    }
}