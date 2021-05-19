package com.example.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoriteActivity(application: Application): AndroidViewModel(application) {
    private var itemUser = MutableLiveData<ArrayList<User>>()

    fun setFavoriteUsers(context: Context){
        val cursor = context.contentResolver.query(
            ContDatabase.FavUserColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val convertItem = Helper.mapCursorToArrayList(cursor)
        itemUser.postValue(convertItem)
    }

    fun getFavoriteUsers():LiveData<ArrayList<User>>{
        return itemUser
    }
}