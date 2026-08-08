package com.example.recipe_browser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipe_browser.model.FavoriteMeal

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(meal: FavoriteMeal)

    @Delete
    suspend fun deleteFavorite(meal: FavoriteMeal)

    @Query("DELETE FROM favorite_meals WHERE idMeal = :id")
    suspend fun deleteFavoriteById(id: String)

    @Query("SELECT * FROM favorite_meals")
    fun getAllFavorites(): LiveData<List<FavoriteMeal>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE idMeal = :id)")
    fun isFavorite(id: String): LiveData<Boolean>
}