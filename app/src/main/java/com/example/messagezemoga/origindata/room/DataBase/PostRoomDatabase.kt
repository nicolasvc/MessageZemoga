package com.example.messagezemoga.origindata.room.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.messagezemoga.origindata.room.dao.PostDao
import com.example.messagezemoga.origindata.room.dao.UserDao
import com.example.messagezemoga.origindata.room.entities.PostEntity
import com.example.messagezemoga.origindata.room.entities.UserEntity


@Database(entities = [PostEntity::class, UserEntity::class], version = 1)
abstract class PostRoomDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao


    companion object {
        @Volatile
        private var instance: PostRoomDatabase? = null
        private val LOCK = Any()

        fun getDataBase(context: Context): PostRoomDatabase = instance ?: synchronized(LOCK) {
            instance ?: builDataBase(context).also { instance = it }
        }

        private fun builDataBase(context: Context) =
            Room.databaseBuilder(context, PostRoomDatabase::class.java, "postsdatabase").build()


    }

}