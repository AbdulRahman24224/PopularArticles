package com.example.populararticles.presentation.article

import com.example.populararticles.entities.Article

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

data class ArticleData (val isIdle :Boolean = true ,val isLoading :Boolean = false ,
                         val articles: MutableList<Article> = mutableListOf() ,
                         val error : String =""
 )


// Actions
sealed class ArticleDetailsIntents {
    data class InititalizeCurrentArticle(val article :Article) : ArticleDetailsIntents()
}
data class ArticleDetails (val isIdle :Boolean = true ,val isLoading :Boolean = false ,
                        val article:Article?=null ,
                        val error : String =""
)