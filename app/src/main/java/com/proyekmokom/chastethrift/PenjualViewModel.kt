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

class PenjualProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUserById(idUser: Int) = liveData(Dispatchers.IO) {
        try {
            val user = userRepository.searchById(idUser)
            emit(user)
        } catch (e: Exception) {
            emit(null)
        }
    }
}

class PenjualProfileViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PenjualProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PenjualProfileViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class PenjualCatalogViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    suspend fun fetchIdUserAndStatusTrue(idUser: Int): LiveData<List<ItemEntity>> {
        return withContext(Dispatchers.IO) {
            itemRepository.fetchIdUserAndStatusTrue(idUser)
        }
    }
}

class PenjualCatalogViewModelFactory(private val itemRepository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PenjualCatalogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PenjualCatalogViewModel(itemRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



class PenjualEditViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    fun getItemById(idItem: Int, onSuccess: (ItemEntity) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = itemRepository.itemById(idItem)
            withContext(Dispatchers.Main) {
                onSuccess(item)
            }
        }
    }

    fun updateItem(gambar:String, nama:String, harga:Int, deskripsi:String?, brand:String?, size:String, id_item: Int, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            itemRepository.updateEdit(gambar, nama, harga, deskripsi, brand, size, id_item)
            onSuccess()
        }
    }

    fun deleteItem(idItem: Int, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            itemRepository.updateStatus(0, idItem)
            onSuccess()
        }
    }
}

class PenjualEditViewModelFactory(private val itemRepository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PenjualEditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PenjualEditViewModel(itemRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}