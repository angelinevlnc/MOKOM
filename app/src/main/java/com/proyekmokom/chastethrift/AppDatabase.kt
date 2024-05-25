package com.proyekmokom.chastethrift

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, ItemEntity::class, KategoriEntity::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun itemDao(): ItemDao
    abstract fun kategoriDao(): KategoriDao

    companion object {
        private var _database: AppDatabase? = null

        fun build(context: Context?): AppDatabase {
            if(_database == null){
                _database = Room.databaseBuilder(context!!,AppDatabase::class.java,"chastethrift").fallbackToDestructiveMigration().build()
            }
            return _database!!
        }
    }
}