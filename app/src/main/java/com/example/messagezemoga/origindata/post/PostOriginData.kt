package com.example.messagezemoga.origindata.post

import com.example.messagezemoga.entities.Post
import com.example.messagezemoga.transversal.constants.RetrofitConstants
import io.reactivex.Single
import retrofit2.http.GET

interface PostOriginData {

    @GET(RetrofitConstants.GET_ALL_POSTS)
    fun getAllPost():Single<List<Post>>

}