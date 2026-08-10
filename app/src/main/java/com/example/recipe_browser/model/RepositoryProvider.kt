package com.example.recipe_browser.model

import android.content.Context
import com.example.recipe_browser.model.local.database.RecipeDatabase
import com.example.recipe_browser.model.repository.RecipeRepository
import com.example.recipe_browser.network.mealApiServices

object RepositoryProvider {

    fun provideRepository(context: Context): RecipeRepository {

        val database =
            RecipeDatabase.getInstance(context)

        return RecipeRepository(
            apiService = mealApiServices,
            recipeDao = database.recipeDao(),
            recentRecipeDao = database.recentRecipeDao(),
            searchHistoryDao = database.searchHistoryDao()
        )
    }
}