package com.example.populararticles.di

import com.example.populararticles.domain.data.ArticlesApis
import com.example.populararticles.domain.data.HtmlApi
import com.example.populararticles.domain.repository.ArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideArticlesRepository(
        articleApi: ArticlesApis,
        serviceApi : HtmlApi
    ): ArticlesRepository {
        return ArticlesRepository(articleApi , serviceApi)
    }
}