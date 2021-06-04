package com.example.populararticles.presentation.article.ui.articles


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import androidx.navigation.fragment.navArgs
import com.example.populararticles.base.compose.preference.Preferences
import com.example.populararticles.base.compose.theme.AppTheme
import com.example.populararticles.presentation.article.ArticleDetailsIntents
import com.example.populararticles.presentation.article.ui.compose.ArticleDetailsScreen
import com.example.populararticles.utils.compose.shouldUseDarkColors
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {


    val args: ArticleDetailsFragmentArgs by navArgs()

    val article by lazy {
        args.article
    }

    @Inject
    lateinit var preferences: Preferences
    private val  viewModel: ArticleDetailsViewmodel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }
    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(requireContext()).apply {

        lifecycleScope.launchWhenStarted {
            viewModel.submitAction(ArticleDetailsIntents.InititalizeCurrentArticle(article))
        }

        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        // We use ViewWindowInsetObserver rather than ProvideWindowInsets
        val windowInsets = ViewWindowInsetObserver(this).start()

        setContent {
            CompositionLocalProvider(
                LocalWindowInsets provides windowInsets
            ) {
                AppTheme(useDarkColors = preferences.shouldUseDarkColors()) {
                    val viewState by viewModel.liveData.observeAsState()
                    viewState?.apply {
                    ArticleDetailsScreen(
                        viewState, preferences.shouldUseDarkColors()
                        /*  actioner = ::onDiscoverAction*/
                    ){}
                   }

                }
            }
        }

    }


}