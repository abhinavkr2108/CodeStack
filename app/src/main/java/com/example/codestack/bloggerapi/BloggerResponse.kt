package com.example.codestack.bloggerapi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class BloggerResponse(
    val description: String,
    val id: String,
    val kind: String,
    val name: String,
    val pages: Pages,
    val posts: Posts,
    val published: String,
    val selfLink: String,
    val updated: String,
    val url: String
)
