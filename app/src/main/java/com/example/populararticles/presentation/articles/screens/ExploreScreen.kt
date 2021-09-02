package com.example.populararticles.presentation.articles.screens

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.populararticles.base.LeafScreen
import com.example.populararticles.entities.Article
import com.example.populararticles.presentation.articles.FilterType
import com.example.populararticles.presentation.articles.viewmodel.ArticleData
import com.example.populararticles.presentation.articles.viewmodel.ArticleIntents
import com.example.populararticles.presentation.articles.viewmodel.ArticlesReduxViewModel


@ExperimentalFoundationApi
@Composable
fun ExploreContent(viewModel : ArticlesReduxViewModel = hiltViewModel(),
                   navController : NavController ){
    val state  = viewModel.liveData.observeAsState(ArticleData())
    val viewState by rememberSaveable{ state }

    if (viewState==null || viewState?.articles.isNullOrEmpty())LaunchedEffect(key1 = Unit, block = {
      viewModel.submitAction(ArticleIntents.RetrieveArticles(FilterType.MONTHLY.value))
    })
        ArticlesListView(viewState ,
            actioner = ::onExploreAction ,
            navController
        )
}

private fun onExploreAction(action: MainFragmentAction, navController: NavController) {
    when (action) {

        SwitchThemeAction -> {
            when(true){
                // todo how to inject preferences here
            /*    true -> {preferences.theme = Preferences.Theme.LIGHT }
                false -> {preferences.theme = Preferences.Theme.DARK }*/
            }
        }
        is OpenShowDetails -> {
            navController.currentBackStackEntry?.arguments =
                Bundle().apply {
                    putSerializable("article", action.article)
                }

            navController.navigate(LeafScreen.ArticleDetails.route)
        }

      //  else -> viewModel.submitAction(action)
    }
}


sealed class MainFragmentAction
object SwitchThemeAction : MainFragmentAction()
data class OpenShowDetails(val article: Article) : MainFragmentAction()