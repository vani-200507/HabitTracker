package com.example.habittracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.habittracker.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(
        username: String,
        password: String
    ): User?

    @Query("SELECT COUNT(*) FROM user")
    suspend fun getUserCount(): Int
}