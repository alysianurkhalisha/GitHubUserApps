package com.example.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object ContDatabase {

    const val AUTHORITY = "com.example.githubuserapps"
    const val SCHEME = "content"

    internal class FavUserColumns : BaseColumns{
        companion object{
            const val photo = "photo"
            const val TABLE_NAME = "favoriteuser"
            const val username = "username"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}