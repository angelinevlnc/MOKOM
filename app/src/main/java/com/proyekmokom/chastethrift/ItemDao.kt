package com.proyekmokom.chastethrift

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {
    @Insert
    fun insert(item:ItemEntity)

    @Update
    fun update(item:ItemEntity)

    @Delete
    fun delete(item:ItemEntity)

    @Query("UPDATE item SET status = :status")
    fun updateStatus(status: Int) // 0 = non-aktif; 1 = aktif

    @Query("SELECT * FROM item")
    fun fetch():List<ItemEntity>

    @Query("SELECT * FROM item where id_user = :id_user")
    fun searchIdUser(id_user:Int):List<ItemEntity> //cari berdasarkan ID User

    @Query("SELECT * FROM item where nama = :nama")
    fun searchNama(nama:String):List<ItemEntity> //cari berdasarkan nama

    @Query("SELECT * FROM item where harga <= :harga AND harga >= :harga")
    fun searchHarga(harga:Int):List<ItemEntity> //cari berdasarkan harga (dalam range harga)

    @Query("SELECT * FROM item where kategori = :kategori")
    fun searchKategori(kategori:String):List<ItemEntity> //cari berdasarkan kategori

    @Query("SELECT * FROM item where brand = :brand")
    fun searchBrand(brand:String):List<ItemEntity> //cari berdasarkan brand

    @Query("SELECT * FROM item where asli = :asli")
    fun searchAsli(asli:Int):List<ItemEntity> //cari berdasarkan asli tidaknya (0 = palsu; 1 = asli)
}