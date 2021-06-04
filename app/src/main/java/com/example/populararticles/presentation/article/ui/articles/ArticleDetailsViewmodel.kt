package com.example.populararticles.presentation.article.ui.articles


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.example.populararticles.base.viewmodel.ReduxViewModel
import com.example.populararticles.di.DefaultDispatcher
import com.example.populararticles.domain.repository.ArticlesRepository
import com.example.populararticles.entities.Article
import com.example.populararticles.presentation.article.ArticleDetails
import com.example.populararticles.presentation.article.ArticleDetailsIntents
import com.example.populararticles.presentation.article.SendSingleItemListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleDetailsViewmodel
@ViewModelInject
constructor(
    private val articleRepository: ArticlesRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher

) : ReduxViewModel<ArticleDetails>(
    ArticleDetails()
) {

    private val pendingActions =
        MutableSharedFlow<ArticleDetailsIntents>()

    init {
        this.viewModelScope.launch(defaultDispatcher) {
            handleIntents()
        }
    }

    private suspend fun handleIntents() {

        pendingActions.collect { action ->
            when (action) {
                is ArticleDetailsIntents.InititalizeCurrentArticle -> setCurrentArticle(action.article)
            }
        }
    }

    fun  setCurrentArticle(article : Article){
        viewModelScope.launch {
            setState { copy(article = article) }
            getArticlefor(article.url)
        }
    }
    fun getArticlefor(url: String = "") {
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