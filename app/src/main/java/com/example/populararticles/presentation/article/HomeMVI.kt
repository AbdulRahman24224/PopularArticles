package com.example.populararticles.presentation.article

import com.example.populararticles.entities.Article

interface ViewState
// Actions
sealed class ArticleIntents {
    data class RetrieveArticles(val period :String) : ArticleIntents()

}

sealed class ArticlesState(
    var articles: MutableList<Article> = mutableListOf()
) : ViewState {
    object Idle : ArticlesState()
    object Loading : ArticlesState()
    class SuccessArticles(list: List<Article> = listOf<Article>()) :
        ArticlesState(list as MutableList<Article>)

    data class Error(val error: String?) : ArticlesState()
}