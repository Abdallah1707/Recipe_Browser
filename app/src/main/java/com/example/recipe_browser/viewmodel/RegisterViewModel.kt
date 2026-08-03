package com.example.recipe_browser.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_browser.room.MealDatabase
import com.example.recipe_browser.room.User
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPref = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _registerStatus = MutableLiveData<RegisterResult>()
    val registerStatus: LiveData<RegisterResult> = _registerStatus

    fun register(name: String, email: String, password: String, confirmPass: String) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
            _registerStatus.value = RegisterResult.Error("Please fill all fields")
            return
        }

        if (password != confirmPass) {
            _registerStatus.value = RegisterResult.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            try {
                val db = MealDatabase.getDatabase(getApplication())
                val existingUser = db.userDao().getUserByEmail(email)
                
                if (existingUser != null) {
                    _registerStatus.value = RegisterResult.Error("User already exists")
                    return@launch
                }

                val newUser = User(email, name, password)
                db.userDao().registerUser(newUser)
                
                with(sharedPref.edit()) {
                    putBoolean("isLoggedIn", true)
                    putString("userEmail", email)
                    apply()
                }
                _registerStatus.value = RegisterResult.Success
            } catch (e: Exception) {
                _registerStatus.value = RegisterResult.Error("Registration failed: ${e.message}")
            }
        }
    }

    sealed class RegisterResult {
        object Success : RegisterResult()
        data class Error(val message: String) : RegisterResult()
    }
}