package com.example.githubuserapps

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDAO {

    @Insert
    suspend fun insert(user: User)

    @Query("delete from favoriteuser where username = :username")
    suspend fun delete(username: String)

    @Query("select * from favoriteuser")
    fun getAllUsers(): LiveData<List<User>>

    @Query("select * from favoriteuser where username = :username")
    fun findUser(username: String) : LiveData<User>

    @Query("select * from favoriteuser")
    fun findProvider(): Cursor
}
