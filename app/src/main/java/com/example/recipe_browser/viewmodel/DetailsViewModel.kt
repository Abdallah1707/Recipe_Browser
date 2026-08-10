package com.example.recipe_browser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.model.local.entities.RecentRecipeEntity
import com.example.recipe_browser.model.repository.RecipeRepository
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _meal = MutableLiveData<Meal?>()
    val meal: LiveData<Meal?> = _meal

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite


    fun loadMeal(id: String) {

        viewModelScope.launch {

            try {
                val meal = repository.lookupMeal(id)
                _meal.value = meal

                if (meal != null) {
                    repository.saveMealsToDatabase(
                        listOf(meal)
                    )

                    repository.insertRecent(
                        RecentRecipeEntity(
                            recipeId = meal.idMeal,
                            openedAt = System.currentTimeMillis()
                        )
                    )
                    checkFavorite(meal.idMeal)
                }
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }
    fun addToRecent(meal: Meal) {

        viewModelScope.launch {

            repository.saveMealsToDatabase(
                listOf(meal)
            )

            repository.insertRecent(
                RecentRecipeEntity(
                    recipeId = meal.idMeal,
                    openedAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun checkFavorite(id: String) {

        viewModelScope.launch {

            try {

                val recipe =
                    repository.getRecipeById(id)

                _isFavorite.value =
                    recipe?.isFavorite ?: false

            } catch (e: Exception) {

                _isFavorite.value = false

            }
        }
    }


    fun toggleFavorite() {
        val currentMeal = _meal.value ?: return
        viewModelScope.launch {
            val recipe =
                repository.getRecipeById(
                    currentMeal.idMeal
                )
            if (recipe != null) {
                val updatedRecipe =
                    recipe.copy(
                        isFavorite = !recipe.isFavorite
                    )
                repository.updateRecipe(
                    updatedRecipe
                )
                _isFavorite.value =
                    updatedRecipe.isFavorite
            }
        }
    }
}