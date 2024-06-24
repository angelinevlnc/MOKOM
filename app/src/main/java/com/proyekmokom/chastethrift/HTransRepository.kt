package com.proyekmokom.chastethrift

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HTransRepository(private val hTransDao: HTransDao) {

    // Insert transaction
    suspend fun insert(hTransEntity: HTransEntity) {
        withContext(Dispatchers.IO) {
            hTransDao.insert(hTransEntity)
        }
    }

    // Update transaction
    suspend fun update(hTransEntity: HTransEntity) {
        withContext(Dispatchers.IO) {
            hTransDao.update(hTransEntity)
        }
    }

    // Delete transaction
    suspend fun delete(hTransEntity: HTransEntity) {
        withContext(Dispatchers.IO) {
            hTransDao.delete(hTransEntity)
        }
    }

    // Update transaction status
    suspend fun updateStatus(status: Int, id_htrans: Int) {
        withContext(Dispatchers.IO) {
            hTransDao.updateStatus(status, id_htrans)
        }
    }

    // Fetch all transactions
    fun fetch(): LiveData<List<HTransEntity>> {
        return hTransDao.fetch()
    }

    // Fetch transactions by seller ID
    fun searchIdPenjual(id_user: Int): LiveData<List<HTransEntity>> {
        return hTransDao.searchIdPenjual(id_user)
    }

    // Fetch transactions by buyer ID
    fun searchIdPembeli(id_user: Int): LiveData<List<HTransEntity>> {
        return hTransDao.searchIdPembeli(id_user)
    }
}
