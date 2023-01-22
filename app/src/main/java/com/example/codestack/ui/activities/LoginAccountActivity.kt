package com.example.codestack.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.codestack.R
import com.example.codestack.databinding.ActivityLoginAccountBinding
import com.example.codestack.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginAccountActivity : AppCompatActivity() {
    lateinit var loginBinding: ActivityLoginAccountBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_account)
        firebaseAuth = FirebaseAuth.getInstance()

        loginBinding.btnLogin.setOnClickListener {
            loginUser()
        }

        loginBinding.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginUser(){
        val email = loginBinding.etLoginEmail.text.toString()
        val password = loginBinding.etLoginPassword.text.toString()

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Some Error Occured!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}