package com.example.codestack.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.codestack.R
import com.example.codestack.bloggerapi.Posts
import com.example.codestack.databinding.ActivityMainBinding
import com.example.codestack.repository.RemoteJobRepository
import com.example.codestack.retrofit.RetrofitInstance
import com.example.codestack.viewmodels.RemoteJobViewModel
import com.example.codestack.viewmodels.RemoteJobViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var remoteJobViewModel: RemoteJobViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)
        setUpViewModel()

    }

    private fun setUpViewModel(){
        val remoteJobRepository = RemoteJobRepository()
        val remoteViewModelFactory = RemoteJobViewModelFactory(application, remoteJobRepository)

        remoteJobViewModel = ViewModelProvider(this, remoteViewModelFactory).get(RemoteJobViewModel::class.java)

    }
}