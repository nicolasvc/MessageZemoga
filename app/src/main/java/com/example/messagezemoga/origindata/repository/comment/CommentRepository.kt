package com.example.messagezemoga.origindata.repository.comment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.messagezemoga.di.application.MyApp
import com.example.messagezemoga.entities.CommentPost
import com.example.messagezemoga.entities.Post
import com.example.messagezemoga.origindata.post.PostClient
import com.example.messagezemoga.origindata.room.DataBase.PostRoomDatabase
import com.example.messagezemoga.origindata.room.dao.PostDao
import com.example.messagezemoga.origindata.room.entities.PostEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.Executors

class CommentRepository {

    private lateinit var listComment: MutableLiveData<List<CommentPost>>


    init {
        val dataBase = PostRoomDatabase.getDataBase(MyApp.applicationContext())
    }


    fun insertPost(postEntity: PostEntity) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            //postDao!!.insertPost(postEntity)
        }
    }

    // fun getAllPostRoom(): LiveData<List<PostEntity>> = listPostRoom


    fun getAllCommentByPost(idPost: String): MutableLiveData<List<CommentPost>> {
        listComment = MutableLiveData()
        PostClient.getClient().getAllCommentByPost(idPost)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    listComment.value = it
                },
                onError = {
                    Log.e("ErrorConsukta", it.toString())
                }
            )
        return listComment
    }
}