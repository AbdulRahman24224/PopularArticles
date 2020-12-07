package com.example.populararticles.presentation.article


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.populararticles.domain.repository.ArticlesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SendSingleItemListener<T>(val item: (item: T) -> Unit) {
    fun sendItem(item: T) = item(item)
}

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesViewModel(
    private val articleRepository: ArticlesRepository = ArticlesRepository.getInstance(),
    val intents: Channel<ArticleIntents> = Channel(Channel.CONFLATED),
    _uiState: MutableStateFlow<ArticlesState> = MutableStateFlow(ArticlesState.Idle)
) : ViewModel() {

    // The UI collects from this StateFlow to get its state updates
    var uiState: MutableStateFlow<ArticlesState> = _uiState
        set(value) {
            val oldStateValue = field.value
            field = value
            when (field.value) {
                is ArticlesState.SuccessArticles -> {
                    oldStateValue.articles.addAll(value.value.articles)
                    field.value.articles = oldStateValue.articles
                }
                else -> {
                    field.value.articles = oldStateValue.articles
                }
            }
        }

    init {
        this.viewModelScope.launch {
            handleIntents()
        }
    }

    fun <T> Flow<T>.runAndCatch(flowResult: SendSingleItemListener<T>) {
        val flow = this
        viewModelScope.launch {
            flow.catch { e -> uiState.value = ArticlesState.Error(e.localizedMessage) }
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

    private fun getArticlesWithin(period: String) {
        uiState.value = ArticlesState.Loading
        articleRepository.getArticlesWithin(period).runAndCatch(SendSingleItemListener
        {
            it.results.apply {
                uiState.value = ArticlesState.SuccessArticles(this)
            }
        })
    }

    // todo scrapping article url for content
    override fun onCleared() {
        intents.cancel()
        super.onCleared()
    }
}