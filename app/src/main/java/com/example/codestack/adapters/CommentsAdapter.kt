package com.example.codestack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.codestack.R
import com.example.codestack.databinding.CommentLayoutBinding
import com.example.codestack.users.Comment

class CommentsAdapter: RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    private val commentCallback = object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }

    val commentDiffer = AsyncListDiffer(this,commentCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val commentPosition = commentDiffer.currentList[position]
        holder.apply {
            commentText.text = commentPosition.comment
            email.text = commentPosition.email
        }
    }

    override fun getItemCount(): Int {
        return commentDiffer.currentList.size
    }

    inner class CommentsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val commentBinding = CommentLayoutBinding.bind(itemView)

        val commentText = commentBinding.commentText
        val email = commentBinding.commentUserMail
    }
}