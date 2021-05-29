package com.example.messagezemoga.origindata.post

import com.example.messagezemoga.entities.CommentPost
import com.example.messagezemoga.entities.Post
import com.example.messagezemoga.origindata.room.entities.UserEntity
import com.example.messagezemoga.transversal.constants.RetrofitConstants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PostOriginData {

    @GET(RetrofitConstants.GET_ALL_POSTS)
    fun getAllPost():Single<List<Post>>

    @GET(RetrofitConstants.GET_COMMENTS_FOR_POST)
    fun getAllCommentByPost(@Path("idPost") idPost:String):Single<List<CommentPost>>

    @GET(RetrofitConstants.GET_ALL_USERS)
    fun getAllUser():Single<List<UserEntity>>

}