package com.example.recipe_browser.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    suspend fun searchMeal(@Query("s") name: String): MealResponse

//    @GET("lookup.php")
//    suspend fun lookupMeal(@Query("i") id: String): MealResponse

    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse

    @GET("search.php")
    suspend fun getMealsByLetter(@Query("f") letter: String): MealResponse
}

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val mealApiServices: MealApiService = retrofit.create(MealApiService::class.java)