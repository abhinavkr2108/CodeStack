package com.example.codestack.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.codestack.R
import com.example.codestack.databinding.ActivityOnBoardingBinding
import com.example.codestack.onboarding.OnBoardingItems
import com.example.codestack.onboarding.OnBoardingItemsAdapter
import com.google.firebase.auth.FirebaseAuth

class OnBoardingActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var onBoardingBinding: ActivityOnBoardingBinding
    lateinit var adapter: OnBoardingItemsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardingBinding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        auth = FirebaseAuth.getInstance()
        setUpOnBoarding()
    }

    private fun setUpOnBoarding(){

        adapter = OnBoardingItemsAdapter(
            listOf(
                OnBoardingItems(
                onBoardingImage = R.drawable.resume,
                title = "Resume Builder",
                description = "Build a Developer Resume with\n our Resume Builder"
                ),
                OnBoardingItems(
                    onBoardingImage = R.drawable.job,
                    title = "Job Search",
                    description = "Find Developer Jobs\n via our app"
                ),
                OnBoardingItems(
                    onBoardingImage = R.drawable.blog,
                    title = "Developer Blogs",
                    description = "Explore Developer Blogs such as Roadmaps, tips and tricks etc."
                ),
                OnBoardingItems(
                    onBoardingImage = R.drawable.connect,
                    title = "Connect with Developers",
                    description = "Connect with more Developers\n through Connect Section"
                ),
                OnBoardingItems(
                    onBoardingImage = R.drawable.getstarted,
                    title = "Get Started",
                    description = "Time to deep dive\n in our app"
                ),
            )
        )

        onBoardingBinding.onBoardingViewPager.adapter = adapter

        if (auth.currentUser!= null){
            redirect("MAIN")
        }

        onBoardingBinding.btnGetStarted.setOnClickListener {
            redirect("LOGIN")
        }
    }

    private fun redirect(status: String){
       val intent = when(status){
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("Failure!")
        }
        startActivity(intent)
        finish()
    }
}