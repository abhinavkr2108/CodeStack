package com.example.codestack.ui.fragments

import android.content.ContentResolver
import android.content.ContentResolver.MimeTypeInfo
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.codestack.R
import com.example.codestack.databinding.FragmentAddPostBinding
import com.example.codestack.users.Post
import com.example.codestack.users.PostDao
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.UploadTask.TaskSnapshot


class AddPostFragment : Fragment() {
    lateinit var addPostBinding:FragmentAddPostBinding
    private lateinit var postDao: PostDao
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var root: DatabaseReference
    private lateinit var storageReference: StorageReference
    private var imageUri: Uri? = null
    val isLiked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)
        addPostBinding = FragmentAddPostBinding.bind(view)
        firebaseDatabase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        root = FirebaseDatabase.getInstance().getReference("Post")
        storageReference = FirebaseStorage.getInstance().getReference("post_images")
        postDao = PostDao()



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  contracts = registerForActivityResult(ActivityResultContracts.GetContent()){
            imageUri = it
            addPostBinding.addPostImage.setImageURI(imageUri)
        }

        addPostBinding.addPostImage.setOnClickListener {
            contracts.launch("image/*")
        }

        addPostBinding.btnPost.setOnClickListener {
            val inputPost = addPostBinding.tvPostDescription.text.toString()
            if (inputPost.isNotEmpty() && imageUri!= null){
                uploadDataToFirebase(imageUri!!, inputPost)
            }
            else{
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun uploadDataToFirebase(imageUri: Uri, postDescription: String){
        val fileReference = storageReference.child("${System.currentTimeMillis()}" + "." + getFileExtension(imageUri))
        fileReference.putFile(imageUri).addOnSuccessListener { taskSnapshot ->
            fileReference.downloadUrl.addOnSuccessListener {
                val imageUrl = it.toString()
                val reference = firebaseDatabase.getReference("Post").push()
                val postKey = reference.key
                activity?.intent?.putExtra("postKey", postKey)

                val post = Post(
                    postDescription,
                    auth.currentUser!!.uid,
                    auth.currentUser!!.email,
                    imageUrl,
                    postKey
                )
                if (postKey != null) {
                    root.child(postKey).setValue(post)
                }
                Toast.makeText(context, "Post Added Successfully", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Some Error Occurred: $it", Toast.LENGTH_LONG).show()
        }
    }


    private fun getFileExtension(imageUri: Uri): String? {
        val contentResolver = context?.contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(contentResolver!!.getType(imageUri))

    }

}
