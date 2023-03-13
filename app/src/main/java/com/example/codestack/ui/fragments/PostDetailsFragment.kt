package com.example.codestack.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.codestack.R
import com.example.codestack.adapters.CommentsAdapter
import com.example.codestack.databinding.FragmentPostDetailsBinding
import com.example.codestack.users.Comment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class PostDetailsFragment : Fragment() {

    private lateinit var postDetailsBinding: FragmentPostDetailsBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var commentReference: DatabaseReference
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var commentList: ArrayList<Comment>
    private val postArgs: PostDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post_details, container, false)

        postDetailsBinding = FragmentPostDetailsBinding.bind(view)
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!
        commentsAdapter = CommentsAdapter()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Up ALl the views
        setUpViews()
        viewComments()
        setUpRecyclerView()

        postDetailsBinding.btnPostComment.setOnClickListener {
            if (postDetailsBinding.commentText.text.isEmpty()){
                Toast.makeText(context, "Comment cannot be Empty", Toast.LENGTH_SHORT).show()
            }
            else{
                addComment()
            }
        }
    }

    private fun setUpViews() {
        postDetailsBinding.apply {
            imgPostDetails.load(postArgs.post.image)
            textPostDetails.text = postArgs.post.description
        }
    }

    private fun addComment(){
        val postKey = arguments?.getString("postKey")
        commentReference = firebaseDatabase.getReference("comment").child(postKey!!).push()
        val commentText = postDetailsBinding.commentText.text.toString()
        val uId = firebaseUser.uid
        val email = firebaseUser.email as String
        val commentKey = commentReference.key!!
        val comment = Comment(uId,email,commentText,commentKey)
        commentReference.setValue(comment).addOnSuccessListener {
            Toast.makeText(context, "Comment Added", Toast.LENGTH_SHORT).show()
            postDetailsBinding.commentText.setText("")
        }.addOnFailureListener {
            Toast.makeText(context, "Some Error Occurred: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewComments(){
        val postKey = arguments?.getString("postKey")

        commentReference = firebaseDatabase.getReference("comment").child(postKey!!)
        commentList = ArrayList()

        commentReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (commentSnap in snapshot.children){
                    val comment = commentSnap.getValue(Comment::class.java)
                    if (comment != null) {
                        commentList.add(comment)
                    }

                }
                commentsAdapter.commentDiffer.submitList(commentList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setUpRecyclerView(){
        postDetailsBinding.rcvComments.apply {
            adapter = commentsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }
    }
}