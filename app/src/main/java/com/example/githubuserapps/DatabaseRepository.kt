package com.example.githubuserapps

import androidx.lifecycle.LiveData

class  DatabaseRepository (private val userDAO: UserDAO) {
    val users: LiveData<List<User>> = userDAO.getAllUsers()
    var findUserDB: LiveData<User>? = null
    suspend fun insert(user: User) {
        userDAO.insert(user)
    }

    fun findUser(username: String) : LiveData<User>? {
        findUserDB = userDAO.findUser(username)
        return findUserDB
    }

    suspend fun delete(username: String) {
        userDAO.delete(username)
    }
}