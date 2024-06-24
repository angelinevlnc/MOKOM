package com.proyekmokom.chastethrift

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository(private val itemDao: ItemDao) {

    // Insert item
    suspend fun insert(item: ItemEntity) {
        withContext(Dispatchers.IO) {
            itemDao.insert(item)
        }
    }

    // Update item
    suspend fun update(item: ItemEntity) {
        withContext(Dispatchers.IO) {
            itemDao.update(item)
        }
    }

    // Delete item
    suspend fun delete(item: ItemEntity) {
        withContext(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }

    // Update item di edit
    suspend fun updateEdit(gambar: String, nama: String, harga: Int, deskripsi: String?, brand: String?, size: String, id_item: Int) {
        withContext(Dispatchers.IO) {
            itemDao.updateEdit(gambar, nama, harga, deskripsi, brand, size, id_item)
        }
    }

    // Update status item
    suspend fun updateStatus(status: Int, id_item: Int) {
        withContext(Dispatchers.IO) {
            itemDao.updateStatus(status, id_item)
        }
    }

    // Update item approval status
    suspend fun updateStatusApproval(status: Int, id_item: Int) {
        withContext(Dispatchers.IO) {
            itemDao.updateStatusApproval(status, id_item)
        }
    }

    // Fetch all items
    fun fetch(): LiveData<List<ItemEntity>> {
        return itemDao.fetch()
    }

    //  Fetch item yg aktif saja
    fun fetchStatusTrue(): LiveData<List<ItemEntity>> {
        return itemDao.fetchStatusTrue()
    }

    // Fetch unapproved items
    fun fetchUnApproved(): LiveData<List<ItemEntity>> {
        return itemDao.fetchUnApproved()
    }

    // Get item by ID
    suspend fun itemById(id_item: Int): ItemEntity {
        return withContext(Dispatchers.IO) {
            itemDao.itemById(id_item)
        }
    }

    // Search items by user ID
    suspend fun searchIdUser(id_user: Int): List<ItemEntity> {
        return withContext(Dispatchers.IO) {
            itemDao.searchIdUser(id_user)
        }
    }

    // Search items by user ID dan status aktif
    suspend fun fetchIdUserAndStatusTrue(id_user: Int): LiveData<List<ItemEntity>> {
        return itemDao.fetchIdUserAndStatusTrue(id_user)
    }

    // Search items by name
    suspend fun searchNama(nama: String): List<ItemEntity> {
        return withContext(Dispatchers.IO) {
            itemDao.searchNama(nama)
        }
    }

    // Search items by price range
    suspend fun searchHarga(harga: Int): List<ItemEntity> {
        return withContext(Dispatchers.IO) {
            itemDao.searchHarga(harga)
        }
    }

    // Search items by brand
    suspend fun searchBrand(brand: String): List<ItemEntity> {
        return withContext(Dispatchers.IO) {
            itemDao.searchBrand(brand)
        }
    }

    // Search items dari asli/tdk/blm dicek admin
    suspend fun searchAsli(asli: Int): List<ItemEntity> {
        return withContext(Dispatchers.IO) {
            itemDao.searchAsli(asli)
        }
    }
}
