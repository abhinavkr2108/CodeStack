package com.example.codestack.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.codestack.R
import com.example.codestack.databinding.FragmentAddPostBinding
import com.example.codestack.users.Post
import com.example.codestack.users.PostDao
import com.example.codestack.users.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AddPostFragment : Fragment() {
    lateinit var addPostBinding: FragmentAddPostBinding
    private lateinit var postDao: PostDao
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)
        addPostBinding = FragmentAddPostBinding.bind(view)
        firebaseDatabase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        postDao = PostDao()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPostBinding.btnPost.setOnClickListener {
            val inputPost = addPostBinding.addPost.text.toString()
            if (inputPost.isNotEmpty()){
                addPost(inputPost)
            }
        }

    }

    fun addPost(postDescription: String){

        val reference = firebaseDatabase.getReference("Post").push()
        val postKey = reference.key
        val email = auth.currentUser!!.email.toString()
        val post = Post(postDescription,auth.currentUser!!.uid,email, postKey.toString())

        reference.setValue(post).addOnSuccessListener {
            Toast.makeText(context, "Post Added Successfully", Toast.LENGTH_SHORT).show()
        }
    }

}