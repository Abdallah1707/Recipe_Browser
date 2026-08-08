package com.example.recipe_browser.repository

import com.example.recipe_browser.database.FavoriteDao
import com.example.recipe_browser.model.FavoriteMeal

class FavoriteRepository(
    private val dao: FavoriteDao
) {

    fun getAllFavorites() =
        dao.getAllFavorites()

    fun isFavorite(id: String) =
        dao.isFavorite(id)

    suspend fun addFavorite(meal: FavoriteMeal) {
        dao.insertFavorite(meal)
    }

    suspend fun removeFavorite(meal: FavoriteMeal) {
        dao.deleteFavorite(meal)
    }

    suspend fun removeFavoriteById(id: String) {
        dao.deleteFavoriteById(id)
    }
}