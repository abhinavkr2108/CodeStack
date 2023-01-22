package com.example.codestack.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.codestack.R
import com.example.codestack.api.Job
import com.example.codestack.databinding.FragmentJobBinding
import com.example.codestack.databinding.FragmentJobDetailsBinding

class JobDetailsFragment : Fragment(R.layout.fragment_job_details) {

    lateinit var jobBinding: FragmentJobDetailsBinding
    lateinit var currentJob: Job
    private val args: JobDetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jobBinding = FragmentJobDetailsBinding.bind(view)
        currentJob = args.job!!

        setUpWebView()
    }

    private fun setUpWebView() {
        jobBinding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let {
                loadUrl(it)
            }

        }
    }

}