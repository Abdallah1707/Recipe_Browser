package com.example.recipe_browser.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("recipes")
data class RecipeEntity(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String,
    val strYoutube: String,
    val strTags: String?,
    val strSource: String?,
    val isFavorite: Boolean = false
)