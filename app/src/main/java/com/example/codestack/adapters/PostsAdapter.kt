package com.example.codestack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.codestack.R
import com.example.codestack.databinding.ItemPostBinding
import com.example.codestack.ui.fragments.ConnectFragmentDirections
import com.example.codestack.users.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class PostsAdapter: RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    var isLiked = false

    private val postCallback = object : DiffUtil.ItemCallback<Post>(){
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
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserId = currentUser?.uid
        val likesReference = FirebaseDatabase.getInstance().getReference("likes")
        val postKey = itemPosition.postKey
        holder.apply {
            userName.text = itemPosition.email
            description.text = itemPosition.description
            image.load(itemPosition.image)

            itemView.setOnClickListener {
                val direction = ConnectFragmentDirections.actionConnectFragmentToPostDetailsFragment(itemPosition, postKey!!)
                it.findNavController().navigate(direction)
            }

            if (postKey != null) {
                postLikedStatus(currentUserId!!,postKey)
            }


            likedButton.setOnClickListener {
                isLiked = true

                likesReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (isLiked==true){
                            if (snapshot.child(postKey!!).hasChild(currentUserId!!)){
                                likesReference.child(postKey).child(currentUserId).removeValue()
                                isLiked = false
                            }
                                else{
                                    likesReference.child(postKey).child(currentUserId).setValue(true)
                                    isLiked = false
                                }


                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })


            }
        }
    }

    override fun getItemCount(): Int {
        return postDiffer.currentList.size
    }

    inner class PostsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val postBinding = ItemPostBinding.bind(itemView)
        val userName = postBinding.tvUserEmail
        val description = postBinding.postDescription
        val image = postBinding.postImage
        val likedButton = postBinding.imgLike
        val commentButton = postBinding.imgComment
        val likesNumber = postBinding.tvLikes

        fun postLikedStatus(currentUserId: String, postKey: String){

            val likesReference = FirebaseDatabase.getInstance().getReference("likes")

            likesReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child(postKey).hasChild(currentUserId!!)){
                        val likesCount = snapshot.child(postKey).childrenCount.toInt()
                        likedButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                        likesNumber.text = "${likesCount} Likes"
                    }
                    else{
                        val likesCount = snapshot.child(postKey).childrenCount.toInt()
                        likedButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        likesNumber.text = "${likesCount} Likes"
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }


    }
}