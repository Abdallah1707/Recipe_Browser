package com.example.recipe_browser.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_meals")
data class RecentMeal(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strCategory: String?,
    val strMealThumb: String?
)