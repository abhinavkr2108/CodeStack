package com.example.codestack.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.codestack.R
import com.example.codestack.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var accountBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        accountBinding.apply {
            loginButton.setOnClickListener {
                val intent = Intent(this@LoginActivity, LoginAccountActivity::class.java)
                startActivity(intent)
            }
            signUpButton.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

}