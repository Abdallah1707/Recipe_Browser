package com.example.recipe_browser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.model.Category
import com.example.recipe_browser.model.repository.RecipeRepository
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val result = repository.getCategories()
                _categories.value = result
            } catch (e: Exception) {
                _categories.value = emptyList()
            }
        }
    }
}