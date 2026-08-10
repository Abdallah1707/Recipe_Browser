package com.example.recipe_browser.model.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recipe_browser.model.local.entities.RecipeEntity
import com.example.recipe_browser.model.local.entities.SearchHistoryEntity


@Dao
interface SearchHistoryDao {

    @Insert
    suspend fun insertSearch(history: SearchHistoryEntity)

    @Query("SELECT * FROM search_history ORDER BY time DESC")
    suspend fun getAllSearchHistory(): List<SearchHistoryEntity>

    @Query("DELETE FROM search_history")
    suspend fun deleteAll()
    @Query("DELETE FROM search_history WHERE keyword = :keyword")
    suspend fun deleteByKeyword(keyword: String)
    @Delete
    suspend fun delete(history: SearchHistoryEntity)
}