package com.example.populararticles.presentation.articles.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.populararticles.base.viewmodel.ReduxViewModel
import com.example.populararticles.di.DefaultDispatcher
import com.example.populararticles.domain.repository.ArticlesRepository
import com.example.populararticles.entities.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ArticleDetailsViewmodel

@Inject constructor(
    private val articleRepository: ArticlesRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher

) : ReduxViewModel<ArticleDetails>(
    ArticleDetails()
) {

    private val pendingActions =
        MutableSharedFlow<ArticleDetailsIntents>()

    init {
        viewModelScope.launch(defaultDispatcher) {
            handleIntents()
        }
    }

    private suspend fun handleIntents() {

        pendingActions.collect { action ->
            when (action) {
                is ArticleDetailsIntents.InitializeCurrentArticle -> setCurrentArticle(action.article)
            }
        }
    }

    private fun  setCurrentArticle(article : Article){
        viewModelScope.launch {
            setState { copy(article = article) }
            getArticlefor(article.url)
        }
    }
    private fun getArticlefor(url: String = "") {
        articleRepository.getArticlefor(url)
            .runAndCatch(SendSingleItemListener
            { str ->
               currentState().article?.let {
                   it.details = str
                   viewModelScope.launch {  setState { copy(article = it) }}
               }


            })
    }

    fun submitAction(action: ArticleDetailsIntents) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

    // Todo should be  generic and added to base ViewModel
    fun <T> Flow<T>.runAndCatch(flowResult: SendSingleItemListener<T>) {
        val flow = this
        viewModelScope.launch(defaultDispatcher) {
            setState { copy(isLoading = true)  }
            flow
                .flowOn(defaultDispatcher)
                .catch { e -> setState { copy(error = e.localizedMessage) } }
                .collect { it ->
                    flowResult.sendItem(it)
                    setState { copy(isLoading = false) }}



        }
    }

}