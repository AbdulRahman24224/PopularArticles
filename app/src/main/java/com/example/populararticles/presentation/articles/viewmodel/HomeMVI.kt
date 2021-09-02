package com.example.populararticles.presentation.articles.viewmodel

import android.os.Parcelable
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
    class SuccessArticles(list: List<Article> ) :
        ArticleStates(list as MutableList<Article>)
    data class Error(val error: String?) : ArticleStates()
}

@Parcelize
data class ArticleData (val isIdle :Boolean = true ,val isLoading :Boolean = false ,
                         val articles: MutableList<Article> = mutableListOf() ,
                         val error : String =""
 ) : Parcelable


// Actions
sealed class ArticleDetailsIntents {
    data class InitializeCurrentArticle(val article :Article) : ArticleDetailsIntents()
}
data class ArticleDetails (val isIdle :Boolean = true ,val isLoading :Boolean = false ,
                        val article:Article?=null ,
                        val error : String =""
)