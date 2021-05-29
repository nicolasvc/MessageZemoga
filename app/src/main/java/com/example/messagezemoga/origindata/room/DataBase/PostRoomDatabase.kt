package com.example.messagezemoga.origindata.room.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.messagezemoga.origindata.room.dao.PostDao
import com.example.messagezemoga.origindata.room.entities.PostEntity


@Database(entities = [PostEntity::class], version = 1)
abstract class PostRoomDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao


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