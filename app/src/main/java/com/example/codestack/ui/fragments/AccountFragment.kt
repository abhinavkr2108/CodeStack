package com.example.codestack.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.codestack.R
import com.example.codestack.databinding.FragmentAccountBinding
import com.example.codestack.ui.activities.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth


class AccountFragment : Fragment(R.layout.fragment_account) {
    lateinit var accountBinding: FragmentAccountBinding
    lateinit var auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountBinding = FragmentAccountBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        accountBinding.tvUserEmail.text = auth.currentUser?.email.toString()

        accountBinding.btnLogout.setOnClickListener {
            auth.signOut()
            val direction = AccountFragmentDirections.actionAccountFragmentToOnBoardingActivity()
            it.findNavController().navigate(direction)
        }
    }

}