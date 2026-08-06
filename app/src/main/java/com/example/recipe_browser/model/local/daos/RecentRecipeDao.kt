package com.example.recipe_browser.model.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipe_browser.model.local.entities.RecentRecipeEntity
import com.example.recipe_browser.model.local.entities.SearchHistoryEntity

@Dao
interface RecentRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecent(recipe: RecentRecipeEntity)
    @Query("SELECT * FROM recent_recipe ORDER BY openedAt DESC")
    fun getRecentRecipes(): List<RecentRecipeEntity>
    @Query("DELETE FROM recent_recipe")
    suspend fun deleteAll()
    @Delete
    suspend fun delete(recipe: RecentRecipeEntity)
}