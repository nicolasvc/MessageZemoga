package com.example.messagezemoga.origindata.repository.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.messagezemoga.di.application.MyApp
import com.example.messagezemoga.entities.Post
import com.example.messagezemoga.origindata.post.PostClient
import com.example.messagezemoga.origindata.room.DataBase.PostRoomDatabase
import com.example.messagezemoga.origindata.room.dao.PostDao
import com.example.messagezemoga.origindata.room.entities.PostEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class PostRepository {

    private lateinit var listPost: MutableLiveData<List<Post>>
    private var listPostRoom: LiveData<List<PostEntity>>
    private var postDao: PostDao? = null


    init {
        val dataBase = PostRoomDatabase.getDataBase(MyApp.applicationContext())
        postDao = dataBase.postDao()
        listPostRoom = postDao!!.getAllPost()
    }


    fun insertPost(postEntity: PostEntity) {
     val executor = Executors.newSingleThreadExecutor()
        executor.execute{
            postDao!!.insertPost(postEntity)
        }
    }

    fun getAllPostRoom(): LiveData<List<PostEntity>> = listPostRoom


    fun getAllPost(): MutableLiveData<List<Post>> {
        listPost = MutableLiveData()
        PostClient.getClient().getAllPost()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    listPost.value = it
                },
                onError = {
                    Log.e("ErrorConsukta", it.toString())
                }
            )
        return listPost
    }

}