package com.proyekmokom.chastethrift

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HTransDao {
    @Insert
    fun insert(item:ItemEntity)

    @Update
    fun update(item:ItemEntity)

    @Delete
    fun delete(item:ItemEntity)

    @Query("UPDATE htrans SET status = :status")
    fun updateStatus(status: Int) // 0 = gagal; 1 = proses; 2 = berhasil

    @Query("SELECT * FROM htrans")
    fun fetch():List<HTransEntity>

    @Query("SELECT * FROM htrans where id_penjual = :id_user")
    fun searchIdPenjual(id_user:Int):List<HTransEntity> //cari berdasarkan ID Penjual

    @Query("SELECT * FROM htrans where id_pembeli = :id_user")
    fun searchIdPembeli(id_user:Int):List<HTransEntity> //cari berdasarkan ID Pembeli
}