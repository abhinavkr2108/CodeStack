package com.example.codestack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.codestack.R
import com.example.codestack.databinding.ItemPostBinding
import com.example.codestack.users.Post

class PostsAdapter: RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    val postCallback = object : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.uId == newItem.uId
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    val postDiffer =AsyncListDiffer(this, postCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val itemPosition = postDiffer.currentList[position]
        holder.apply {
            userName.text = itemPosition.email
            description.text = itemPosition.description
        }
    }

    override fun getItemCount(): Int {
        return postDiffer.currentList.size
    }

    inner class PostsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val postBinding = ItemPostBinding.bind(itemView)
        val userName = postBinding.tvUsername
        val description = postBinding.tvDescription


    }
}