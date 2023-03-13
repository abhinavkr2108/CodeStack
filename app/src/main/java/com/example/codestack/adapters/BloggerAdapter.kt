package com.example.codestack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.codestack.R
import com.example.codestack.databinding.BlogItemsBinding
import com.example.codestack.newsapi.Article

import com.example.codestack.ui.fragments.BlogsFragmentDirections
import com.squareup.picasso.Picasso


class BloggerAdapter:ListAdapter<Article, BloggerAdapter.BloggerViewHolder>(BloggerCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BloggerViewHolder {
        return BloggerViewHolder(( LayoutInflater.from(parent.context).inflate(R.layout.blog_items,parent,false)))
    }

    override fun onBindViewHolder(holder: BloggerViewHolder, position: Int) {
        getItem(position).let {
            holder.apply {
                title.text = it.title
                publisher.text = it.publishedAt
                description.text = it.description
               // Glide.with(BlogsFragment()).load(it.urlToImage).error(R.drawable.defaultimage).into(image)
                Picasso.get().load(it.urlToImage).error(R.drawable.defaultimage).into(image)
                itemView.setOnClickListener {
                    val direction = BlogsFragmentDirections.actionBlogsFragmentToNewsDetails(getItem(position))
                    it.findNavController().navigate(direction)
                }
            }

        }
    }

    inner class BloggerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val blogItemsBinding = BlogItemsBinding.bind(itemView)
        val title = blogItemsBinding.tvTitle
        val publisher = blogItemsBinding.tvPublisher
        val description = blogItemsBinding.tvDescription
        val image = blogItemsBinding.ivArticleImage

    }

}