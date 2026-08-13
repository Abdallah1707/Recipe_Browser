package com.example.recipe_browser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.model.Meal
import com.example.recipe_browser.model.local.entities.SearchHistoryEntity
import com.example.recipe_browser.model.repository.RecipeRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _searchResults =
        MutableLiveData<List<Meal>>()

    val searchResults: LiveData<List<Meal>> =
        _searchResults

    private val _searchHistory =
        MutableLiveData<List<SearchHistoryEntity>>()

    val searchHistory: LiveData<List<SearchHistoryEntity>> =
        _searchHistory

    fun searchMeal(query: String) {

        android.util.Log.d(
            "SEARCH_DEBUG",
            "searchMeal CALLED -> $query"
        )
        viewModelScope.launch {

            try {

                val cleanQuery = query.trim()

                if (cleanQuery.isEmpty()) return@launch

                val meals = repository.searchMeal(cleanQuery)

                _searchResults.value =
                    meals.sortedBy { it.strMeal }

                val history =
                    repository.getSearchHistory()

                val alreadyExists =
                    history.any {
                        it.keyword.equals(
                            cleanQuery,
                            ignoreCase = true
                        )
                    }

                if (!alreadyExists) {

                    repository.insertSearch(
                        SearchHistoryEntity(
                            keyword = cleanQuery,
                            time = System.currentTimeMillis()
                        )
                    )
                }

                loadSearchHistory()

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun loadSearchHistory() {

        viewModelScope.launch {

            try {

                val history =
                    repository.getSearchHistory()

                _searchHistory.postValue(history)

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
    fun clearSearchHistory() {

        viewModelScope.launch {

            try {

                repository.clearSearchHistory()
                loadSearchHistory()

            } catch (e: Exception) {

                e.printStackTrace()

            }
        }
    }
}