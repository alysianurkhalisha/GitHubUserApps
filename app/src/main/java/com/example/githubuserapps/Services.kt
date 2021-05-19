package com.example.githubuserapps

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Services {

    @GET("/search/users")
    @Headers("Authorization: token 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")
    fun searchUser(@Query("q") key: String): Call<Keluaran>

    @GET("/users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(@Path("username") username: String?): Call<User>

    @GET("/users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String?): Call<List<User>>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String?): Call<List<User>>

}