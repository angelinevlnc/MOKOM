package com.proyekmokom.chastethrift

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface KategoriDao {
    @Insert
    fun insert(kategori:KategoriEntity)

    @Update
    fun update(kategori:KategoriEntity)

    @Delete
    fun delete(kategori:KategoriEntity)

    @Query("UPDATE kategori SET status = :status")
    fun updateStatus(status: Int) // 0 = non-aktif; 1 = aktif

    @Query("SELECT * FROM kategori")
    fun fetch():List<KategoriEntity>

    @Query("SELECT * FROM kategori where nama = :nama")
    fun searchNama(nama:String):List<KategoriEntity> //cari berdasarkan nama
}