package com.proyekmokom.chastethrift

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Silahkan mengubah isi dari adapter untuk kenyamanan
 * Kerjakan juga inisialisasi table database disini
 */

@Entity(tableName = "item")
data class ItemEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_item") val id_item:Int?, // ? artinya boleh NULL
    @ColumnInfo(name = "id_user") val id_user:Int, // ID User yg membuat
    @ColumnInfo(name = "gambar") val gambar:String,
    @ColumnInfo(name = "nama") val nama:String,
    @ColumnInfo(name = "harga") val harga:Int,
    @ColumnInfo(name = "deskripsi") val deskripsi:String?, //boleh NULL
    @ColumnInfo(name = "brand") val brand:String?, //boleh NULL
    @ColumnInfo(name = "size") val size:String,
    @ColumnInfo(name = "asli") val asli:Int?, // 0 = palsu; 1 = asli (boleh NULL)
    @ColumnInfo(name = "status") val status:Int = 1 // 0 = non-aktif; 1 = aktif
)