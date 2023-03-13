package com.example.codestack.users
import java.io.Serializable


data class Post(
    val description: String? = null,
    val uId: String?= null,
    val email: String? = null,
    val image: String? = null,
    var postKey: String? = null
): Serializable
