package com.example.anitest.room

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class UserRepository(private val dao: UserDao) {
    val user: Flow<UserEntity> = dao.getUserById(1)

    suspend fun insert(user: UserEntity) {
        dao.insert(user)
    }

    fun getUserById(): Flow<UserEntity> {
        return dao.getUserById(1)
    }

    fun delete(user: UserEntity) {
        dao.delete(user)
    }

    suspend fun updateUserById(name: String, img: Int) {
        if(name != "") {
            dao.updateUserNameById(1, name)
        }
        if(img != 0) {
            dao.updateUserImgById(1, img)
        }
    }
}


