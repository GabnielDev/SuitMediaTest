package com.example.suitmediatest.room

import androidx.room.*
import androidx.room.Insert
import androidx.room.Query
import com.example.suitmediatest.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user WHERE id=:user_id")
    suspend fun getUser(user_id: Int): List<User>

    @Query("SELECT * FROM user")
    suspend fun getListUser(): List<User>

}