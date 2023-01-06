package com.example.applicationnews

import com.example.applicationsomething.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//All articles mentioning Apple from yesterday, sorted by popular publishers first
//https://newsapi.org/v2/everything?q=apple&from=2023-01-01&to=2023-01-01&sortBy=popularity&apiKey=ccb6c64a2a11496889b3a688cd4b7ebf
//Top business headlines in the US right now
//https://newsapi.org/v2/top-headlines?country=in&apiKey=ccb6c64a2a11496889b3a688cd4b7ebf

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "ccb6c64a2a11496889b3a688cd4b7ebf"


interface NewsAPI {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country:String, @Query("page") page:Int) : Call<News>
}
object NewsService{
    val newsInstance : NewsAPI
    init {
        val appRetrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        newsInstance = appRetrofit.create(NewsAPI::class.java)
    }
}