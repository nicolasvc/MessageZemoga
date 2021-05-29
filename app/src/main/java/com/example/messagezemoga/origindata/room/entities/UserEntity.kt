package com.example.messagezemoga.origindata.room.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Users")
class UserEntity(
    /*@SerializedName("address")
    val address: Address,
    @SerializedName("company")
    val company: Company,*/
    @SerializedName("email")
    val email: String, // Sincere@april.biz
    @SerializedName("id")
    @PrimaryKey
    val id: Int, // 1
    @SerializedName("name")
    val name: String, // Leanne Graham
    @SerializedName("phone")
    val phone: String, // 1-770-736-8031 x56442
    @SerializedName("username")
    val username: String, // Bret
    @SerializedName("website")
    val website: String // hildegard.org
)