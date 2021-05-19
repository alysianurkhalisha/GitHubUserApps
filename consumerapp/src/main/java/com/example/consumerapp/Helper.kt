package com.example.consumerapp

import android.database.Cursor
import java.util.*

object Helper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<User> {
        val itemUser = ArrayList<User>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val username =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContDatabase.FavUserColumns.username))
                val photo =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContDatabase.FavUserColumns.photo))
                itemUser.add(
                    User(
                        username,
                        photo
                    )
                )
            }
        }
        return itemUser
    }
}
