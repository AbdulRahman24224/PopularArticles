package com.example.populararticles.presentation.articles.viewmodel

import android.os.Parcelable
import com.example.populararticles.base.viewmodel.BaseState
import com.example.populararticles.entities.Article
import kotlinx.parcelize.Parcelize


interface ViewState
// Actions
sealed class ArticleIntents {
    data class RetrieveArticles(val period :String) : ArticleIntents()
}

sealed class ArticleStates(
    var articles: MutableList<Article> = mutableListOf()
) {
    object Idle : ArticleStates()
    object Loading : ArticleStates()
    class SuccessArticles(list: List<Article> ) : ArticleStates(list as MutableList<Article>)
    data class Error(val error: String?) : ArticleStates()
}

@Parcelize
data class ArticleData (val isIdle :Boolean = true ,
                         val articles: MutableList<Article> = mutableListOf() ,
                        override var error: String ="",
                        override var isLoading: Boolean= false

 ) : BaseState(),Parcelable


// Actions
sealed class ArticleDetailsIntents {
    data class InitializeCurrentArticle(val article :Article) : ArticleDetailsIntents()
}
data class ArticleDetails (val isIdle :Boolean = true , val article:Article?=null ,
                           override var error: String ="",
                           override var isLoading: Boolean = false) : BaseState()