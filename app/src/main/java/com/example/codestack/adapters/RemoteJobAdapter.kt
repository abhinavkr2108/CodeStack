package com.example.codestack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load

import com.example.codestack.R
import com.example.codestack.api.Job
import com.example.codestack.databinding.JobLayoutAdpaterBinding
import com.example.codestack.ui.fragments.JobFragmentDirections
import com.squareup.picasso.Picasso

class RemoteJobAdapter: ListAdapter<Job, RemoteJobAdapter.RemoteJobViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobViewHolder {
        return RemoteJobViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.job_layout_adpater,parent,false))
    }

    override fun onBindViewHolder(holder: RemoteJobViewHolder, position: Int) {
        getItem(position).let {
            holder.apply {
                date.text = it.publication_date
                jobTitle.text = it.title
                jobType.text = it.job_type
                jobLocation.text = it.candidate_required_location
                companyName.text = it.company_name
                image.load(it.company_logo_url)

                itemView.setOnClickListener {
                    val direction = JobFragmentDirections.actionJobFragmentToJobDetailsFragment(getItem(position))
                    it.findNavController().navigate(direction)
                }
            }
        }
    }

    inner class RemoteJobViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val rvBinding = JobLayoutAdpaterBinding.bind(itemView)
        val date = rvBinding.tvDate
        val jobTitle = rvBinding.tvJobTitle
        val jobType = rvBinding.tvJobType
        val jobLocation = rvBinding.tvJobLocation
        val image = rvBinding.ivCompanyLogo
        val companyName = rvBinding.tvCompanyName


    }
}