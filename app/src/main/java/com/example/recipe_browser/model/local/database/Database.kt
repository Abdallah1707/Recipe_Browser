package com.example.recipe_browser.model.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipe_browser.model.local.daos.RecentRecipeDao
import com.example.recipe_browser.model.local.daos.RecipeDao
import com.example.recipe_browser.model.local.daos.SearchHistoryDao
import com.example.recipe_browser.model.local.entities.RecentRecipeEntity
import com.example.recipe_browser.model.local.entities.RecipeEntity
import com.example.recipe_browser.model.local.entities.SearchHistoryEntity


@Database(
    entities = [
        RecipeEntity::class,
        SearchHistoryEntity::class,
        RecentRecipeEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun recentRecipeDao(): RecentRecipeDao

    companion object {

        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}