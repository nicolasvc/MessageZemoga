package com.example.messagezemoga.origindata.viewmodel.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.messagezemoga.entities.Post
import com.example.messagezemoga.origindata.repository.post.PostRepository
import com.example.messagezemoga.origindata.room.entities.PostEntity

class PostViewModel : ViewModel() {

    private val postRepository = PostRepository()

    fun getAllPost(): LiveData<List<Post>> = postRepository.getAllPost()

    fun getAllPostRom(): LiveData<List<PostEntity>> = postRepository.getAllPostRoom()

    fun insertPost(postEntity: PostEntity) = postRepository.insertPost(postEntity)

    fun updatePost(postEntity: PostEntity) = postRepository.updatePost(postEntity)

    fun deleteAllPost() = postRepository.deleteAllPost()

    fun deletePostById(idPost: Int) = postRepository.deletePost(idPost)

    fun getPostById(idPost: Int) = postRepository.getPostById(idPost)

    fun getAllPostFav() = postRepository.getAllFavPost()


}