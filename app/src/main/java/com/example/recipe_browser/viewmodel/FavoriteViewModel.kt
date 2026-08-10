package com.example.recipe_browser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.model.local.entities.RecipeEntity
import com.example.recipe_browser.model.repository.RecipeRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _favorites =
        MutableLiveData<List<RecipeEntity>>()

    val favorites: LiveData<List<RecipeEntity>> =
        _favorites


    fun loadFavorites() {
        viewModelScope.launch {
            try {
                _favorites.value =
                    repository.getFavoriteRecipes()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite(recipe: RecipeEntity) {
        viewModelScope.launch {
            val updatedRecipe =
                recipe.copy(
                    isFavorite = !recipe.isFavorite
                )
            repository.updateRecipe(updatedRecipe)
            loadFavorites()
        }
    }
}