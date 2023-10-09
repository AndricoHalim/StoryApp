package com.andricohalim.storyapp

data class UserModel (
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)