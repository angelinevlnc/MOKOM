package com.proyekmokom.chastethrift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.ViewModelProvider

class AdminProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUserById(idUser: Int) = liveData(Dispatchers.IO) {
        try {
            val user = userRepository.searchById(idUser)
            emit(user)
        } catch (e: Exception) {
            emit(null)
        }
    }
}

class AdminProfileViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AdminProfileViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}