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

class EditProfileViewModel(private val db: AppDatabase) : ViewModel() {
    private val _user = MutableLiveData<UserEntity?>()
    val user: LiveData<UserEntity?> get() = _user

    fun getUserById(idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedUser = db.userDao().searchById(idUser)
            _user.postValue(fetchedUser)
        }
    }

    fun updateUserProfile(username: String, newPassword: String, idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            db.userDao().updateProfile(username, newPassword, idUser)
        }
    }

    fun deleteUser(idUser: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            db.userDao().updateStatus(0, idUser)
        }
    }
}

class EditProfileViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditProfileViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class PembeliProfileViewModel(private val db: AppDatabase) : ViewModel() {
    fun getUserById(idUser: Int) = liveData(Dispatchers.IO) {
        try {
            val user = db.userDao().searchById(idUser)
            emit(user)
        } catch (e: Exception) {
            emit(null)
        }
    }
}

class PembeliProfileViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PembeliProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PembeliProfileViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class HomeViewModel(private val db: AppDatabase) : ViewModel() {
    suspend fun fetchItems(): List<ItemEntity> {
        return withContext(Dispatchers.IO) {
            db.itemDao().fetchStatusTrue()
        }
    }
}

class HomeViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}