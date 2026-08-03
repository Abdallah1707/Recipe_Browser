package com.example.recipe_browser.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.room.MealDatabase
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPref = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _loginStatus = MutableLiveData<LoginResult>()
    val loginStatus: LiveData<LoginResult> = _loginStatus

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _loginStatus.value = LoginResult.Error("Please fill all fields")
            return
        }

        viewModelScope.launch {
            try {
                val db = MealDatabase.getDatabase(getApplication())
                val user = db.userDao().getUserByEmail(email)
                
                if (user != null && user.password == password) {
                    with(sharedPref.edit()) {
                        putBoolean("isLoggedIn", true)
                        putString("userEmail", email)
                        apply()
                    }
                    _loginStatus.value = LoginResult.Success
                } else {
                    _loginStatus.value = LoginResult.Error("Invalid email or password")
                }
            } catch (e: Exception) {
                _loginStatus.value = LoginResult.Error("Database error: ${e.message}")
            }
        }
    }

    sealed class LoginResult {
        object Success : LoginResult()
        data class Error(val message: String) : LoginResult()
    }
}