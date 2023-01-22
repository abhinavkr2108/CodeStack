package com.example.codestack.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.codestack.bloggerapi.BloggerResponse
import com.example.codestack.newsapi.Article
import com.example.codestack.newsapi.NewsResponse

class BloggerCallBack: DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}