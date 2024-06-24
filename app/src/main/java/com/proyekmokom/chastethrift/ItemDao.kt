package com.proyekmokom.chastethrift

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item:ItemEntity)

    @Update
    suspend fun update(item:ItemEntity)

    @Delete
    suspend fun delete(item:ItemEntity)

    @Query("UPDATE item SET gambar=:gambar, nama=:nama, harga=:harga, deskripsi=:deskripsi, brand=:brand, size=:size WHERE id_item = :id_item")
    suspend fun updateEdit(gambar:String, nama:String, harga:Int, deskripsi:String?, brand:String?, size:String, id_item: Int) // utk hlm Edit Item penjual

    @Query("UPDATE item SET status = :status WHERE id_item = :id_item")
    suspend fun updateStatus(status: Int, id_item: Int) // 0 = non-aktif; 1 = aktif

    @Query("UPDATE item SET asli = :status WHERE id_item = :id_item")
    suspend fun updateStatusApproval(status: Int, id_item: Int) // 0 = non-aktif; 1 = aktif

    @Query("SELECT * FROM item")
    fun fetch():LiveData<List<ItemEntity>>

    @Query("SELECT * FROM item where status = 1")
    fun fetchStatusTrue(): LiveData<List<ItemEntity>> //munculin hanya Item yg masih ada

    @Query("SELECT * FROM item where asli = 0")
    fun fetchUnApproved():LiveData<List<ItemEntity>> //tampilkan semua item yang butuh approval admin

    @Query("SELECT * FROM item WHERE id_item = :id_item")
    suspend fun itemById(id_item: Int): ItemEntity

    @Query("SELECT * FROM item where id_user = :id_user")
    suspend fun searchIdUser(id_user:Int):List<ItemEntity> //cari berdasarkan ID User

    @Query("SELECT * FROM item where id_user = :id_user AND status = 1")
    fun fetchIdUserAndStatusTrue(id_user:Int):LiveData<List<ItemEntity>> //cari berdasarkan ID User dan Status True (Item masih ada)

    @Query("SELECT * FROM item where nama = :nama")
    suspend fun searchNama(nama:String):List<ItemEntity> //cari berdasarkan nama

    @Query("SELECT * FROM item where harga <= :harga AND harga >= :harga")
    suspend fun searchHarga(harga:Int):List<ItemEntity> //cari berdasarkan harga (dalam range harga)

    @Query("SELECT * FROM item where brand = :brand")
    suspend fun searchBrand(brand:String):List<ItemEntity> //cari berdasarkan brand

    @Query("SELECT * FROM item where asli = :asli")
    suspend fun searchAsli(asli:Int):List<ItemEntity> //cari berdasarkan asli tidaknya (0 = palsu; 1 = asli)
}