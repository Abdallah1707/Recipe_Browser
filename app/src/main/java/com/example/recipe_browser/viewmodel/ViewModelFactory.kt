package com.example.recipe_browser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_browser.model.repository.RecipeRepository

class ViewModelFactory(
    private val repository: RecipeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return when {

            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(repository) as T

            modelClass.isAssignableFrom(SearchViewModel::class.java) ->
                SearchViewModel(repository) as T

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) ->
                FavoriteViewModel(repository) as T

            modelClass.isAssignableFrom(DetailsViewModel::class.java) ->
                DetailsViewModel(repository) as T

            modelClass.isAssignableFrom(RecentViewModel::class.java) ->
                RecentViewModel(repository) as T

            else ->
                throw IllegalArgumentException(
                    "Unknown ViewModel class: ${modelClass.name}"
                )
        }
    }
}