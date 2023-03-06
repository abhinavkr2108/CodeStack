package com.example.codestack.users

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao{
//    val db = FirebaseFirestore.getInstance()
//    val postCollections = db.collection("posts")
//    val auth = Firebase.auth
//
////    fun addPost(text: String){
////        val userId = auth.currentUser!!.uid
////        GlobalScope.launch {
////            val userDao = UserDao()
////            val user = userDao.getUserById(userId).await().toObject(User::class.java)!!
////
////            val post = Post(text,user)
////            postCollections.document().set(post)
////        }
////
////    }
}
