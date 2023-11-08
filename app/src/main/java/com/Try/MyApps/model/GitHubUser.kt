package com.Try.MyApps.model

data class GitHubUser(
    val login: String,
    val id: Int,
    val username: Int,
    val token: Int,
    val avatar_url: String,
    val name: String?,
    val followers: Int,
    val following: Int,
    val html_url: String,
    var isFavorite: Boolean = false
    )
