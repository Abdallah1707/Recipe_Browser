package com.example.recipe_browser.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val keyword: String,
    val time: Long
)