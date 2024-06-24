package com.proyekmokom.chastethrift

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    // Insert user
    suspend fun insert(user: UserEntity) {
        userDao.insert(user)
    }

    // Update user
    suspend fun update(user: UserEntity) {
        userDao.update(user)
    }

    // Delete user
    suspend fun delete(user: UserEntity) {
        userDao.delete(user)
    }

    // Update profile
    suspend fun updateProfile(username: String, password: String, id_user: Int) {
        userDao.updateProfile(username, password, id_user)
    }

    // Update status
    suspend fun updateStatus(status: Int, id_user: Int) {
        userDao.updateStatus(status, id_user)
    }

    // Fetch all users
    fun fetch(): LiveData<List<UserEntity>> {
        return userDao.fetch()
    }

    // Get user dari username
    suspend fun get(username: String): List<UserEntity> {
        return userDao.get(username)
    }

    // Cek login
    suspend fun cek(username: String, password: String): List<UserEntity> {
        return userDao.cek(username, password)
    }

    // Search user by ID
    suspend fun searchById(id_user: Int): UserEntity {
        return userDao.searchById(id_user)
    }
}
