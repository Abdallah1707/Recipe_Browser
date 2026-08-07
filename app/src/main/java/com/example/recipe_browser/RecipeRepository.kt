package com.example.recipe_browser

import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.model.MealApiService
import com.example.recipe_browser.model.local.daos.RecentRecipeDao
import com.example.recipe_browser.model.local.daos.RecipeDao
import com.example.recipe_browser.model.local.daos.SearchHistoryDao

class RecipeRepository(
    private val apiService: MealApiService,
    private val recentDao: RecentRecipeDao,
    private val recipeDao: RecipeDao,
    private val searchHistoryDao: SearchHistoryDao
    //add favoriteDao
) {
    //  Popular recipes

    suspend fun getPopularRecipes(count: Int = 10): List<Meal> {
        return (1..count).mapNotNull {
            apiService.getRandomMeal().meals?.firstOrNull()
        }
    }

}