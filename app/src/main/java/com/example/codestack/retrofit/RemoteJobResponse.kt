package com.example.codestack.retrofit

import com.example.codestack.api.Job
import com.example.codestack.api.jobRespone
import retrofit2.Call
import retrofit2.http.GET

interface RemoteJobResponse {

    @GET("remote-jobs")
    fun getRemoteJobResponse(): Call<jobRespone>
}