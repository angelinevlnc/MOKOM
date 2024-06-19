package com.proyekmokom.chastethrift

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Silahkan mengubah isi dari adapter untuk kenyamanan
 * Kerjakan juga inisialisasi table database disini
 */

@Entity(tableName = "htrans")
data class HTransEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_htrans") val id_htrans:Int?, // ? artinya boleh NULL
    @ColumnInfo(name = "id_penjual") val id_penjual:Int,
    @ColumnInfo(name = "id_pembeli") val id_pembeli:Int,
    @ColumnInfo(name = "total_harga") val total:Int,
    @ColumnInfo(name = "status") val status:Int = 1 // 0 = gagal; 1 = proses; 2 = berhasil
)