package com.example.codestack.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codestack.R
import com.example.codestack.adapters.BloggerAdapter
import com.example.codestack.databinding.FragmentBlogsBinding
import com.example.codestack.ui.activities.MainActivity
import com.example.codestack.viewmodels.RemoteJobViewModel


class BlogsFragment : Fragment(R.layout.fragment_blogs) {
    val bloggerAdapter = BloggerAdapter()
    lateinit var blogBinding: FragmentBlogsBinding
    lateinit var remoteJobViewModel: RemoteJobViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        remoteJobViewModel = (activity as MainActivity).remoteJobViewModel
        blogBinding = FragmentBlogsBinding.bind(view)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        blogBinding.rvBlogs.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = bloggerAdapter
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL){})
        }
        ObserveData()
    }

    private fun ObserveData(){
        remoteJobViewModel.getTechnologyNews().observe(viewLifecycleOwner, Observer {
            if (it!=null){
                bloggerAdapter.submitList(it.articles)
            }
        })
    }
}