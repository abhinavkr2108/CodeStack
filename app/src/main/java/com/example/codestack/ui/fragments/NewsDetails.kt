package com.example.codestack.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.codestack.R
import com.example.codestack.api.Job
import com.example.codestack.databinding.FragmentJobDetailsBinding
import com.example.codestack.databinding.FragmentNewsDetailsBinding
import com.example.codestack.newsapi.Article


class NewsDetails : Fragment(R.layout.fragment_news_details) {
    lateinit var newsBinding: FragmentNewsDetailsBinding
    lateinit var currentArticle: Article
    private val args: NewsDetailsArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsBinding = FragmentNewsDetailsBinding.bind(view)
        currentArticle = args.news!!

        setUpWebView()
    }

    private fun setUpWebView() {
        newsBinding.newsWebView.apply {
            webViewClient = WebViewClient()
            currentArticle.url?.let { loadUrl(it) }

        }
    }
}