package com.example.codestack.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.codestack.repository.RemoteJobRepository

class RemoteJobViewModel(val app: Application, private val repository: RemoteJobRepository): AndroidViewModel(app) {

    fun remoteJobResult() = repository.remoteJobResult()
    fun getTechnologyNews() = repository.newsResult()
}