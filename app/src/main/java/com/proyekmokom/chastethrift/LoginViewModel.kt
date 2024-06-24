package com.proyekmokom.chastethrift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyekmokom.chastethrift.AppDatabase
import com.proyekmokom.chastethrift.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.ViewModelProvider

class LoginViewModel(private val db: AppDatabase) : ViewModel() {
    fun loginUser(username: String, password: String, onSuccess: (Int, Int) -> Unit, onError: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = db.userDao().cek(username, password)
            if (user.isNotEmpty()) {
                val role = user.first().role
                val idUser = user.first().id_user!!
                withContext(Dispatchers.Main) {
                    onSuccess(role, idUser)
                }
            } else {
                withContext(Dispatchers.Main) {
                    onError()
                }
            }
        }
    }
}

class LoginViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class RegisterViewModel(private val db: AppDatabase) : ViewModel() {
    fun registerUser(
        username: String,
        password: String,
        role: Int,
        onSuccess: (Int, Int) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Check if username already exists
                val user = db.userDao().get(username)
                if (user.isEmpty()) {
                    // Insert new user
                    val newUser = UserEntity(
                        id_user = null,
                        username = username,
                        password = password,
                        role = role
                    )
                    db.userDao().insert(newUser)

                    // Fetch inserted user (assuming fetch() returns a list of users)
                    val insertedUser = db.userDao().fetch().lastOrNull()
                    if (insertedUser != null) {
                        withContext(Dispatchers.Main) {
                            onSuccess(insertedUser.role, insertedUser.id_user!!)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            onError("Failed to fetch user after registration")
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Username sudah ada!")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e.message ?: "Error during registration")
                }
            }
        }
    }
}

class RegisterViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
