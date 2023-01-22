package com.example.codestack.api

data class jobRespone(
    val `0-legal-notice`: String,
    val `00-warning`: String,
    val jobCount: Int,
    val jobs: List<Job>
)