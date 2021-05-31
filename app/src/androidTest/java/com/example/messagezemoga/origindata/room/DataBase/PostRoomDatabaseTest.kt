package com.example.messagezemoga.origindata.room.DataBase

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.messagezemoga.origindata.room.dao.PostDao
import com.example.messagezemoga.origindata.room.dao.UserDao
import com.example.messagezemoga.origindata.room.entities.PostEntity
import com.example.messagezemoga.origindata.room.entities.UserEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class PostRoomDatabaseTest : TestCase(){
    private lateinit var postDao: PostDao
    private lateinit var userDao: UserDao
    private lateinit var db: PostRoomDatabase

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, PostRoomDatabase::class.java
        ).build()
        postDao = db.postDao()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeUserAndPost() = runBlocking {
        val post =  PostEntity("Test","Body",2,1,false,false)
        val user = UserEntity("test@Gmial",1,"Zemogga app","312214324","Arthas","Www.burningcrusade.com")
        postDao.insertPost(post)
        userDao.inserUser(user)
    }
}