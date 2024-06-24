package com.proyekmokom.chastethrift

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.withContext

class EditProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _user = MutableLiveData<UserEntity?>()
    val user: LiveData<UserEntity?> get() = _user

    fun getUserById(idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedUser = userRepository.searchById(idUser)
            _user.postValue(fetchedUser)
        }
    }

    fun updateUserProfile(username: String, newPassword: String, idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateProfile(username, newPassword, idUser)
        }
    }

    fun deleteUser(idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateStatus(0, idUser)
        }
    }
}

class EditProfileViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditProfileViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class PembeliProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUserById(idUser: Int) = liveData(Dispatchers.IO) {
        try {
            val user = userRepository.searchById(idUser)
            emit(user)
        } catch (e: Exception) {
            emit(null)
        }
    }
}

class PembeliProfileViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PembeliProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PembeliProfileViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class HomeViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    suspend fun fetchItems(): LiveData<List<ItemEntity>> {
        return withContext(Dispatchers.IO) {
            itemRepository.fetchStatusTrue()
        }
    }
}

class HomeViewModelFactory(private val itemRepository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(itemRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}