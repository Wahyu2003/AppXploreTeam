package com.Try.MyApps.network

import com.Try.MyApps.model.GitHubUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
        @GET("users/{username}")
        fun getUserDetails(@Path("username") username: String): Call<GitHubUser>
    }
