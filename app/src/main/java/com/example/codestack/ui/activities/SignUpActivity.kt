package com.example.codestack.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.codestack.R
import com.example.codestack.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var signUpBinding: ActivitySignUpBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        signUpBinding.btnSignUp.setOnClickListener{
            SignUpUser()
        }
        signUpBinding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginAccountActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun SignUpUser(){
        signUpBinding.apply {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                Toast.makeText(this@SignUpActivity, "Please Enter All The Fields", Toast.LENGTH_SHORT).show()
                return
            }

            if (confirmPassword!=password){
                Toast.makeText(this@SignUpActivity, "Password and Confirm Password Don't Match", Toast.LENGTH_LONG).show()
                return
            }


            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@SignUpActivity) {
                    if (it.isSuccessful){
                        Toast.makeText(this@SignUpActivity, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this@SignUpActivity, "Some Error Occured!!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}