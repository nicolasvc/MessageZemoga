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

    @Query("SELECT * FROM Posts WHERE isFavorite")
    fun getAllPostFav(): LiveData<List<PostEntity>>

    @Query("SELECT * FROM Posts where id=:idPost ")
    fun getPostById(idPost: Int): LiveData<PostEntity>

    @Insert
    fun insertPost(post: PostEntity)

    @Update
    fun updatePost(post: PostEntity)

    @Query("DELETE FROM Posts")
    fun deleteAllPost()

    @Query("DELETE FROM Posts where id=:idPost")
    fun deletePost(idPost: Int)
    //endregion


}