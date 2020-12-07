package com.example.populararticles.domain.repository

import com.example.populararticles.domain.data.ArticlesApis
import com.example.populararticles.entities.ArticlesResponse
import com.example.populararticles.domain.data.articlesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import com.example.populararticles.domain.data.Result
import kotlinx.coroutines.flow.flowOn

class ArticlesRepository private constructor(
    private val service: ArticlesApis = articlesService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,

    ) {

    fun getArticlesWithin(period: String) =
        flow<ArticlesResponse> {
            val articlesResponse = service.getArticlesWithin(period)
            when (articlesResponse) {
                is Result.Success -> {
                    emit(articlesResponse.data!!)
                }
                is Result.Failure -> {
                }
            }

        }.flowOn(defaultDispatcher)


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: ArticlesRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ArticlesRepository().also { instance = it }
            }
    }
}