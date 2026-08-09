package com.example.recipe_browser

import com.example.recipe_browser.model.Category
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.network.MealApiService
import com.example.recipe_browser.network.mealApiServices

class RecipeRepository(
    private val apiService: MealApiService,

) {
    suspend fun getMeals(): List<Meal> {
        return mealApiServices.searchMeal("a").meals ?: emptyList()
    }
    suspend fun getRandomMeal(): Meal? {
        return mealApiServices.getRandomMeal().meals?.firstOrNull()
    }
    suspend fun getMealDetails(id: String): Meal {
        return mealApiServices.lookupMeal(id).meals!!.first()
    }

    suspend fun getCategories(): List<Category> {
        return mealApiServices.getCategories().categories
    }
    suspend fun searchMeals(name: String): List<Meal> {
        return mealApiServices.searchMeal(name).meals ?: emptyList()
    }

    //  Popular recipes
    suspend fun getPopularRecipes(count: Int = 10): List<Meal> {
        return (1..count).mapNotNull {
            apiService.getRandomMeal().meals?.firstOrNull()
        }
    }
    suspend fun getMealsByCategory(category: String): List<Meal> {

        return mealApiServices
            .filterByCategory(category)
            .meals ?: emptyList()
    }

}