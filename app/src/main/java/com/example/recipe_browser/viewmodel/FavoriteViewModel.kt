package com.example.recipe_browser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.database.AppDatabase
import com.example.recipe_browser.model.FavoriteMeal
import com.example.recipe_browser.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val dao =
        AppDatabase.getDatabase(application).favoriteDao()

    private val repository =
        FavoriteRepository(dao)

    val favorites =
        repository.getAllFavorites()

    fun isFavorite(id: String) =
        repository.isFavorite(id)

    fun addFavorite(meal: FavoriteMeal) {
        viewModelScope.launch {
            repository.addFavorite(meal)
        }
    }

    fun removeFavorite(meal: FavoriteMeal) {
        viewModelScope.launch {
            repository.removeFavorite(meal)
        }
    }

    fun removeFavoriteById(id: String) {
        viewModelScope.launch {
            repository.removeFavoriteById(id)
        }
    }
}