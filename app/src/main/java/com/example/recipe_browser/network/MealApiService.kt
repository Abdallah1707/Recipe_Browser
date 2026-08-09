package com.example.recipe_browser.network

import com.example.recipe_browser.model.CategoryResponse
import com.example.recipe_browser.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("search.php")
    suspend fun searchMeal(
        @Query("s") name: String
    ): MealResponse

    @GET("lookup.php")
    suspend fun lookupMeal(
        @Query("i") id: String
    ): MealResponse

    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse


}