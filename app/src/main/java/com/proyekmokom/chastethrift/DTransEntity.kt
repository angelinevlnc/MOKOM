package com.proyekmokom.chastethrift

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Silahkan mengubah isi dari adapter untuk kenyamanan
 * Kerjakan juga inisialisasi table database disini
 */

@Entity(tableName = "dtrans")
data class DTransEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_dtrans") val id_kategori:Int?, // ? artinya boleh NULL
    @ColumnInfo(name = "id_htrans") val id_htrans:Int,
    @ColumnInfo(name = "id_item") val id_item:Int,
    @ColumnInfo(name = "nama_item") val nama_item:String,
    @ColumnInfo(name = "harga_item") val harga:Int,
    @ColumnInfo(name = "status") val status:Int = 1 // 0 = non-aktif; 1 = aktif
)