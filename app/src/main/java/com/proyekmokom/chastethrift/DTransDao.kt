package com.proyekmokom.chastethrift

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DTransDao {
    @Insert
    fun insert(item:ItemEntity)

    @Update
    fun update(item:ItemEntity)

    @Delete
    fun delete(item:ItemEntity)

    @Query("UPDATE dtrans SET status = :status")
    fun updateStatus(status: Int) // 0 = non-aktif; 1 = aktif

    @Query("SELECT * FROM dtrans")
    fun fetch():List<DTransEntity>

    @Query("SELECT * FROM dtrans where id_htrans = :id_htrans")
    fun searchIdHTrans(id_htrans:Int):List<DTransEntity> //cari berdasarkan ID HTrans
}