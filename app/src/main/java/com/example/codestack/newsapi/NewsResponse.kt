package com.example.codestack.newsapi

import com.example.codestack.newsapi.Article


data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)