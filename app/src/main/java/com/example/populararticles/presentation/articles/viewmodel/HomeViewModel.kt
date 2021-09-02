package com.example.populararticles.presentation.articles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.populararticles.base.viewmodel.ReduxViewModel
import com.example.populararticles.di.DefaultDispatcher
import com.example.populararticles.domain.repository.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SendSingleItemListener<T>(val item: (item: T) -> Unit) {
    fun sendItem(item: T) = item(item)
}

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ArticlesViewModel
@Inject constructor(
    private val articleRepository: ArticlesRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher

) : ViewModel() {

    val intents: Channel<ArticleIntents> = Channel(Channel.CONFLATED)
    val _uiState: MutableStateFlow<ArticleStates> = MutableStateFlow(ArticleStates.Idle)

    var statesList = mutableListOf<ArticleStates>()

    private fun changeState (state: ArticleStates){
        _uiState .value = state
        statesList.add(state)
    }

    // state reducer
    var uiState: MutableStateFlow<ArticleStates> = _uiState
        set(value) {
            val oldStateValue = field.value
            field = value
          when (field.value) {
                is ArticleStates.SuccessArticles -> {
                    oldStateValue.articles.addAll(value.value.articles)
                    field.value.articles = oldStateValue.articles
                }
                else -> {
                    field.value.articles = oldStateValue.articles
                }
            }

        }


    init {
        this.viewModelScope.launch(defaultDispatcher) {

            handleIntents()

        }
    }

    fun <T> Flow<T>.runAndCatch(flowResult: SendSingleItemListener<T>) {
        val flow = this
        viewModelScope.launch(defaultDispatcher) {
            flow
                .flowOn(defaultDispatcher)
                .catch { e -> changeState(ArticleStates.Error(e.localizedMessage)) }
                .collect { it -> flowResult.sendItem(it) }

        }
    }


    private suspend fun handleIntents() {
        intents.consumeAsFlow().collect {
            when (it) {
                is ArticleIntents.RetrieveArticles -> getArticlesWithin(it.period)
            }
        }
    }

     fun getArticlesWithin(period: String) {
         changeState( ArticleStates.Loading )
        articleRepository.getArticlesWithin(period)
            .runAndCatch(SendSingleItemListener
        {

            it.apply {
                when(status){
                    "false" ->  changeState(ArticleStates.Error(error))
                    else  -> changeState(ArticleStates.SuccessArticles(results))
                }

            }
        })
    }


    override fun onCleared() {
        intents.cancel()
        super.onCleared()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ArticlesReduxViewModel
@Inject constructor(
    private val articleRepository: ArticlesRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher

) : ReduxViewModel<ArticleData>(ArticleData()) {

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

    fun getArticlesWithin(period: String) {
        articleRepository.getArticlesWithin(period).runAndCatch(SendSingleItemListener
        {
            it.apply {
                when(status){
                    "false" ->  viewModelScope.launch { setState { copy(error = error) } }
                    else  -> viewModelScope.launch {
                        currentState().articles.addAll(results)
                        setState { copy(articles = currentState().articles) }
                    }
                }
            }
        })
    }

    fun submitAction(action: ArticleIntents) {
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