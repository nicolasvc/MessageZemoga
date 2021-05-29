package com.example.messagezemoga.origindata.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.messagezemoga.origindata.room.entities.UserEntity


@Dao
interface UserDao {

    @Query("SELECT * FROM Users WHERE id=:idUser")
    fun getUserById(idUser:Int) : LiveData<UserEntity>

    @Insert
    fun inserUser(userEntity: UserEntity)

}