package com.proyekmokom.chastethrift

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HTransDao {
    @Insert
    suspend fun insert(htrans:HTransEntity)

    @Update
    suspend fun update(htrans:HTransEntity)

    @Delete
    suspend fun delete(htrans:HTransEntity)

    @Query("UPDATE htrans SET status = :status where id_htrans = :id_htrans")
    suspend fun updateStatus(status: Int, id_htrans:Int) // 0 = gagal; 1 = proses; 2 = berhasil

    @Query("SELECT * FROM htrans")
    fun fetch(): LiveData<List<HTransEntity>>

    @Query("SELECT * FROM htrans where id_penjual = :id_user")
    fun searchIdPenjual(id_user:Int):LiveData<List<HTransEntity>> //cari berdasarkan ID Penjual

    @Query("SELECT * FROM htrans where id_pembeli = :id_user")
    fun searchIdPembeli(id_user:Int):LiveData<List<HTransEntity>> //cari berdasarkan ID Pembeli
}