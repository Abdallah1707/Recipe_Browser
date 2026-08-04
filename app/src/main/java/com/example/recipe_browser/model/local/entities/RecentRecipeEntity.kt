package com.example.recipe_browser.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("recent_recipe")
data class RecentRecipeEntity(
    @PrimaryKey
    val recipeId: String,
    val openedAt: Long
)