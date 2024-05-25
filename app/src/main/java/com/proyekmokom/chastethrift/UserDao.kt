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

    @Query("UPDATE user SET status = :status")
    fun updateStatus(status: Int) // 0 = non-aktif; 1 = aktif

    @Query("SELECT * FROM user")
    fun fetch():List<UserEntity>

    @Query("SELECT * FROM user where username = :username")
    fun get(username:String):List<UserEntity> //cek username sama saat register/edit profile

    @Query("SELECT * FROM user where username = :username AND password = :password")
    fun cek(username:String, password:String):List<UserEntity> //cek login
}