package com.example.codestack.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.codestack.repository.RemoteJobRepository

class RemoteJobViewModelFactory(val app: Application, private val repository: RemoteJobRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RemoteJobViewModel(app, repository) as T
    }
}