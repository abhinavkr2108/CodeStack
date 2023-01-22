package com.example.codestack.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codestack.R
import com.example.codestack.adapters.RemoteJobAdapter
import com.example.codestack.databinding.FragmentJobBinding
import com.example.codestack.ui.activities.MainActivity
import com.example.codestack.viewmodels.RemoteJobViewModel


class JobFragment : Fragment(R.layout.fragment_job) {
    lateinit var remoteJobViewModel: RemoteJobViewModel
    lateinit var jobBinding: FragmentJobBinding
    val remoteJobAdapter = RemoteJobAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        remoteJobViewModel = (activity as MainActivity).remoteJobViewModel
        jobBinding = FragmentJobBinding.bind(view)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        jobBinding.rvNotes.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = remoteJobAdapter
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL){})
        }
        ObserveData()
    }

    private fun ObserveData(){
        remoteJobViewModel.remoteJobResult().observe(viewLifecycleOwner, Observer {
            if (it!=null){
                remoteJobAdapter.submitList(it.jobs)
            }
        })
    }


}