package com.example.recipe_browser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.model.local.entities.RecipeEntity
import com.example.recipe_browser.model.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecentViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recentRecipes =
        MutableLiveData<List<RecipeEntity>>()

    val recentRecipes: LiveData<List<RecipeEntity>> =
        _recentRecipes

    fun loadRecentRecipes() {

        viewModelScope.launch {

            try {
                _recentRecipes.value =
                    repository.getRecentRecipes()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}