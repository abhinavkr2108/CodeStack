package com.example.codestack.users

import android.content.ClipDescription

data class Post(
    val description: String? = null,
    val uId: String?= null,
    val email: String? = null,
    var postKey: String? = null
)
