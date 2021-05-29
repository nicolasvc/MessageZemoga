package com.example.messagezemoga.origindata.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.messagezemoga.origindata.room.entities.PostEntity


@Dao
interface PostDao {

    //region Table Posts
    @Query("SELECT * FROM Posts")
    fun getAllPost(): LiveData<List<PostEntity>>

    @Insert
    fun insertPost(post:PostEntity)

    @Update
    fun updatePost(post: PostEntity)

    @Query("DELETE FROM Posts")
    fun deleteAllPost()
    //endregion



}