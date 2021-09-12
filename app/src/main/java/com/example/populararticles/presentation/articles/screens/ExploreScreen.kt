package com.example.populararticles.presentation.articles.screens

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.populararticles.R
import com.example.populararticles.base.LeafScreen
import com.example.populararticles.entities.Article
import com.example.populararticles.presentation.articles.FilterType
import com.example.populararticles.presentation.articles.viewmodel.ArticleData
import com.example.populararticles.presentation.articles.viewmodel.ArticleIntents
import com.example.populararticles.presentation.articles.viewmodel.HomeViewModel


@ExperimentalFoundationApi
@Composable
fun ExploreContent(viewModel : HomeViewModel = hiltViewModel(),
                   navController : NavController ){
    val state  = viewModel.liveData.observeAsState(ArticleData())
    val viewState by rememberSaveable{ state }

    if (viewState==null || viewState.articles.isNullOrEmpty())
        LaunchedEffect(key1 = Unit, block = {
      viewModel.submitAction(ArticleIntents.RetrieveArticles(FilterType.MONTHLY.value))
    })


    Column( ) {
        ArticlesListView(viewState ,
            actioner = ::onExploreAction ,
            navController
        )

        if (viewState.error.isNotEmpty())
            Text(text = stringResource(R.string.profile) ,
           Modifier
                .size(80.dp)
                .background(Color.Blue)
                .padding(80.dp)  ,
            style = MaterialTheme.typography.body1 ,
            maxLines = 3 ,
        )


    }

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