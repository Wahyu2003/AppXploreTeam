package com.Try.MyApps.network

import com.google.gson.annotations.SerializedName

data class GithubUser(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val name: String,
    val username: String,
    val bio: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int
)
