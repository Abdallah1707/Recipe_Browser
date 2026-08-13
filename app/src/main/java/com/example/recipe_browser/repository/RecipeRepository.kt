package com.example.recipe_browser.model.repository

import com.example.recipe_browser.model.Category
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.model.local.daos.RecipeDao
import com.example.recipe_browser.model.local.daos.RecentRecipeDao
import com.example.recipe_browser.model.local.daos.SearchHistoryDao
import com.example.recipe_browser.model.local.entities.RecipeEntity
import com.example.recipe_browser.model.local.entities.RecentRecipeEntity
import com.example.recipe_browser.model.local.entities.SearchHistoryEntity
import com.example.recipe_browser.network.MealApiService

class RecipeRepository(
    private val apiService: MealApiService,
    private val recipeDao: RecipeDao,
    private val recentRecipeDao: RecentRecipeDao,
    private val searchHistoryDao: SearchHistoryDao
) {

    // ---------- API ----------
    suspend fun searchMeal(name: String): List<Meal> {
        return apiService.searchMeal(name).meals ?: emptyList()
    }
    suspend fun lookupMeal(id: String): Meal? {
        return apiService.lookupMeal(id).meals?.firstOrNull()
    }
    suspend fun getRandomMeal(): Meal? {
        return apiService.getRandomMeal().meals?.firstOrNull()
    }
    suspend fun getCategories(): List<Category> {
        return apiService.getCategories().categories
    }
    // ---------- ROOM ----------
    suspend fun getAllRecipes(): List<RecipeEntity> {
        return recipeDao.getAllRecipes()
    }
    suspend fun getFavoriteRecipes(): List<RecipeEntity> {
        return recipeDao.getFavoriteRecipes()
    }
    suspend fun getRecipeById(id: String): RecipeEntity? {
        return recipeDao.getRecipeById(id)
    }
    suspend fun updateRecipe(recipe: RecipeEntity) {
        recipeDao.updateRecipe(recipe)
    }
    // ---------- SEARCH HISTORY ----------
    // ---------- SEARCH HISTORY ----------

    suspend fun insertSearch(search: SearchHistoryEntity) {

        searchHistoryDao.deleteByKeyword(search.keyword)

        searchHistoryDao.insertSearch(search)
    }
    suspend fun getSearchHistory(): List<SearchHistoryEntity> {
        return searchHistoryDao.getAllSearchHistory()
    }
    suspend fun clearSearchHistory() {
        searchHistoryDao.deleteAll()
    }
//    suspend fun insertSearch(search: SearchHistoryEntity) {
//        searchHistoryDao.insertSearch(search)
//    }
//    suspend fun getSearchHistory() =
//        searchHistoryDao.getAllSearchHistory()
    // ---------- RECENT ----------
    suspend fun insertRecent(recipe: RecentRecipeEntity) {
        recentRecipeDao.insertRecent(recipe)
    }
    suspend fun getRecentRecipes(): List<RecipeEntity> {
        val recentRecipes =
            recentRecipeDao.getRecentRecipes()
        return recentRecipes.mapNotNull { recent ->
            recipeDao.getRecipeById(recent.recipeId)
        }
    }

    // ---------- API → ROOM ----------
    suspend fun saveMealsToDatabase(meals: List<Meal>) {
        val recipes = meals.map { meal ->

            val oldRecipe = recipeDao.getRecipeById(meal.idMeal)

            RecipeEntity(
                idMeal = meal.idMeal,
                strMeal = meal.strMeal,
                strCategory = meal.strCategory ?: "",
                strArea = meal.strArea ?: "",
                strInstructions = meal.strInstructions ?: "",
                strMealThumb = meal.strMealThumb ?: "",
                strYoutube = meal.strYoutube ?: "",
                strTags = meal.strTags,
                strSource = meal.strSource,
                isFavorite = oldRecipe?.isFavorite ?: false
            )
        }

        recipeDao.insertRecipes(recipes)
    }
}