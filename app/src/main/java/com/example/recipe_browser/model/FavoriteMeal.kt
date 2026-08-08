package com.example.recipe_browser.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meals")
data class FavoriteMeal(

    @PrimaryKey
    val idMeal: String,

    val strMeal: String,

    val strCategory: String?,

    val strArea: String?,

    val strInstructions: String?,

    val strMealThumb: String?,

    val strYoutube: String?
)