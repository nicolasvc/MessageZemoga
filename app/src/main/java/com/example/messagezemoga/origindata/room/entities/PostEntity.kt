package com.example.messagezemoga.origindata.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Posts")
class PostEntity(
    val body: String,
    val title: String,
    val userId: Int,
    @PrimaryKey
    var id: Int ,
    val isRead: Boolean,
    val isFavorite: Boolean
)