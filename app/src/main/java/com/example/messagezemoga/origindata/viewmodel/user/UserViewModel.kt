package com.example.messagezemoga.origindata.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.messagezemoga.origindata.repository.user.UserRepository
import com.example.messagezemoga.origindata.room.entities.UserEntity

class UserViewModel : ViewModel() {

    val userRepository = UserRepository()

    fun getUserById(idUser: Int) :LiveData<UserEntity> = userRepository.getUserById(idUser)

    fun insertUser(userEntity: UserEntity) = userRepository.insertUser(userEntity)

    fun getAllUser() = userRepository.getAllUser()
}