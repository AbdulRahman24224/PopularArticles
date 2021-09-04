package com.example.populararticles.presentation.articles.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.populararticles.base.viewmodel.BaseViewModel
import com.example.populararticles.di.DefaultDispatcher
import com.example.populararticles.domain.repository.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SendSingleItemListener<T>(val item: (item: T) -> Unit) {
    fun sendItem(item: T) = item(item)
}

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ArticlesReduxViewModel
@Inject constructor(
    private val articleRepository: ArticlesRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher

) : BaseViewModel<ArticleData>(ArticleData() ,dispatcher) {

    private val pendingActions = MutableSharedFlow<ArticleIntents>()

    init {
        viewModelScope.launch(defaultDispatcher) {
            handleIntents()
        }
    }

    private suspend fun handleIntents() {

        pendingActions.collect { action ->
            when (action) {
               is ArticleIntents.RetrieveArticles -> getArticlesWithin(action.period)
            }
        }
    }

    fun submitAction(action: ArticleIntents) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

   private fun getArticlesWithin(period: String) {

        articleRepository.getArticlesWithin(period).runAndCatch( SendSingleItemListener { b -> viewModelScope.launch { setState { copy(isLoading = b) }} } ,
            SendSingleItemListener
        {
            it.apply {
                when(status){
                    "false" ->  viewModelScope.launch { setState { currentState().apply { error = it.error }} }
                    else  -> viewModelScope.launch {
                        currentState().articles.addAll(results)
                        setState { copy(articles = currentState().articles) }
                    }
                }
            }
        })
    }

}