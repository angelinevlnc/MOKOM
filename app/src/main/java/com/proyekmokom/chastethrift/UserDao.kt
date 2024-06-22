package com.proyekmokom.chastethrift

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    fun insert(user:UserEntity)

    @Update
    fun update(user:UserEntity)

    @Delete
    fun delete(user:UserEntity)

    @Query("UPDATE user SET username = :username, password = :password where id_user = :id_user")
    fun updateProfile(username: String, password:String, id_user:Int) // Update Username & Password utk Edit Profile

    @Query("UPDATE user SET status = :status where id_user = :id_user")
    fun updateStatus(status: Int, id_user:Int) // 0 = non-aktif; 1 = aktif

    @Query("SELECT * FROM user")
    fun fetch():List<UserEntity>

    @Query("SELECT * FROM user where username = :username")
    fun get(username:String):List<UserEntity> //cek username sama saat register/edit profile

    @Query("SELECT * FROM user where username = :username AND password = :password AND status = 1")
    fun cek(username:String, password:String):List<UserEntity> //cek login

    @Query("SELECT * FROM user where id_user = :id_user")
    fun searchById(id_user:Int):UserEntity //search berdasarkan idUser -> hasilnya pasti cuma 1 sehingga bkn List
}