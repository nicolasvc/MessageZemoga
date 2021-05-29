package com.example.messagezemoga.origindata.viewmodel.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.messagezemoga.entities.CommentPost
import com.example.messagezemoga.origindata.repository.comment.CommentRepository

class CommentViewModel : ViewModel() {

    private val commentRepository = CommentRepository()

    fun getAllPost(idPost: String): LiveData<List<CommentPost>> = commentRepository.getAllCommentByPost(idPost)

}