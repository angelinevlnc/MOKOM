package com.proyekmokom.chastethrift

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Silahkan mengubah isi dari adapter untuk kenyamanan
 * Kerjakan juga inisialisasi table database disini
 */

@Entity(tableName = "kategori")
data class KategoriEntity (
    @PrimaryKey
    @ColumnInfo(name = "id_kategori") val id_kategori:Int?, // ? artinya boleh NULL
    @ColumnInfo(name = "nama") val nama:String,
    @ColumnInfo(name = "status") val status:Int = 1 // 0 = non-aktif; 1 = aktif
)