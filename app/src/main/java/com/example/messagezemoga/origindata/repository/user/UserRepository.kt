package com.example.messagezemoga.origindata.repository.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.messagezemoga.di.application.MyApp
import com.example.messagezemoga.origindata.post.PostClient
import com.example.messagezemoga.origindata.room.DataBase.PostRoomDatabase
import com.example.messagezemoga.origindata.room.dao.UserDao
import com.example.messagezemoga.origindata.room.entities.UserEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class UserRepository {

    private var userDao: UserDao? = null

    init {
        val dataBase = PostRoomDatabase.getDataBase(MyApp.applicationContext())
        userDao = dataBase.userDao()
    }

    fun getUserById(idUser: Int) : LiveData<UserEntity> = userDao!!.getUserById(idUser)

    fun insertUser(userEntity: UserEntity){
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            userDao!!.inserUser(userEntity)
        }
    }

    fun getAllUser(){
        PostClient.getClient().getAllUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    it.forEach { userEntity ->  insertUser(userEntity) }
                },
                onError = {
                    Log.e("ErrorConsukta", it.toString())
                }
            )
    }

}