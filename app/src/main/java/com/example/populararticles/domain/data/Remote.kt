package com.example.populararticles.domain.data

import com.example.populararticles.entities.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

private const val SERVER_BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/viewed/"
private const val API_KEY = "api-key"
private const val API_KEY_VALUE = "k32jrKem6G6bfDG15r1AVUtwCs0DIqNh"


/*
private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(SERVER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory( MyCallAdapterFactory())
        .build()
}

val articlesService: ArticlesApis by lazy {
    retrofit.create(ArticlesApis::class.java)
}
*/


interface ArticlesApis {

    @GET("{period}.json")
   suspend fun getArticlesWithin(
        @Path("period") period: String,
        @Query(API_KEY) appId: String = API_KEY_VALUE
    ):Result<ArticlesResponse>

}

interface HtmlApi {

    @GET()
    suspend fun fetchUrl(@Url url : String): Result<String>
}