package com.example.codestack.retrofit


import com.example.codestack.newsapi.NewsResponse
import com.example.codestack.utils.Constants.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
     fun getTechnologyNews(
        @Query("country")countryCode:String="in",
        @Query("category")category:String="technology",
        @Query("apiKey")apiKey: String=API_KEY
    ): Call<NewsResponse>

}