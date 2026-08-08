package com.example.recipe_browser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipe_browser.model.RecentMeal

@Dao
interface RecentMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecent(meal: RecentMeal)

    @Query("""
        SELECT * FROM recent_meals
        ORDER BY rowid DESC
        LIMIT 5
    """)
    fun getRecentMeals(): LiveData<List<RecentMeal>>

    @Query("DELETE FROM recent_meals WHERE idMeal = :id")
    suspend fun deleteRecent(id: String)
}
