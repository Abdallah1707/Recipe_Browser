package com.example.recipe_browser.model.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.recipe_browser.model.local.entities.RecipeEntity
@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<RecipeEntity>)
    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<RecipeEntity>
    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    suspend fun getFavoriteRecipes(): List<RecipeEntity>
    @Query("SELECT * FROM recipes WHERE idMeal = :id")
    suspend fun getRecipeById(id: String): RecipeEntity?
    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()
    @Delete
    suspend fun delete(recipe: RecipeEntity)
    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)
}