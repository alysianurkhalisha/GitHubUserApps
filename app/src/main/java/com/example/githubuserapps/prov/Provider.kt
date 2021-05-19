package com.example.githubuserapps.prov

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuserapps.UserDAO
import com.example.githubuserapps.UserDatabase

class Provider : ContentProvider() {
    private lateinit var userrdao: UserDAO

    companion object {
        private val TABLE_NAME = "favoriteuser"
        private const val AUTHORITY = "com.example.githubuserapps"
        private const val FAV = 1


        private val apiMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            apiMatcher.addURI(AUTHORITY, TABLE_NAME, FAV)
        }
    }

    override fun onCreate(): Boolean {
        userrdao = context?.let { ctx ->
            UserDatabase.getDatabase(ctx)?.userDAO()
        }!!
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (
            apiMatcher.match(uri)) {
            FAV -> {
                cursor = userrdao.findProvider()
                if (context != null) {
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}
