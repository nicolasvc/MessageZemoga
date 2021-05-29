package com.example.messagezemoga.origindata.viewmodel.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.messagezemoga.entities.Post
import com.example.messagezemoga.origindata.repository.post.PostRepository
import com.example.messagezemoga.origindata.room.entities.PostEntity

class PostViewModel1 : ViewModel() {

    private val postRepository = PostRepository()

    fun getAllPost(): LiveData<List<Post>> = postRepository.getAllPost()

    fun getAllPostRom(): LiveData<List<PostEntity>> = postRepository.getAllPostRoom()

    fun insertPost(postEntity: PostEntity) = postRepository.insertPost(postEntity)


}