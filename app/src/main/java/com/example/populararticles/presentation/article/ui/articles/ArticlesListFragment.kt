package com.example.populararticles.presentation.article.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.ProgressBar
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.populararticles.base.compose.preference.Preferences
import com.example.populararticles.base.compose.theme.AppTheme
import com.example.populararticles.presentation.article.*
import com.example.populararticles.presentation.article.ui.compose.Main
import com.example.populararticles.utils.compose.shouldUseDarkColors
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ArticlesActivity]
 * in two-pane mode (on tablets) or a [ArticleDetailsActivity]
 * on handsets.
 */


@AndroidEntryPoint
class ArticlesListFragment : Fragment() {

    private val  viewModel: ArticlesReduxViewModel by viewModels ()
    val liveData : LiveData<ArticleData> by lazy { viewModel.liveData }
   // lateinit var articleAdapter: ArticlesAdapter
    lateinit var rvArticles: RecyclerView
    lateinit var progress: ProgressBar


    @Inject
    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(requireContext()).apply {
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
                      Main(this ,
                          actioner = ::onDiscoverAction
                      )
                    }

                }
            }
        }

    }

    private fun onDiscoverAction(action: MainFragmentAction) {
        when (action) {

        /*     -> findNavController().navigate("app.tivi://account".toUri())
            is OpenShowDetails -> {
                var uri = "app.tivi://show/${action.showId}"
                if (action.episodeId != null) {
                    uri += "/episode/${action.episodeId}"
                }
                findNavController().navigate(uri.toUri(), DefaultNavOptions)
            }

            else -> viewModel.submitAction(action)*/
            SwitchThemeAction -> {
                when(true){
                    true -> {preferences.theme = Preferences.Theme.LIGHT }
                    false -> {preferences.theme = Preferences.Theme.DARK }
                }
            }
            is OpenShowDetails -> {
                findNavController(this).navigate(
                    ArticlesListFragmentDirections.actionArticlesListFragmentToDetailsFragment(
                         action.article
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      /*  liveData.observe(this , Observer {
            it.apply {
                progress.visibility = visibilityBasedon(isLoading)
                articleAdapter.updateList(articles.toMutableList())
                Toast.makeText(requireContext(),error, Toast.LENGTH_SHORT).show()
                Log.e("ERROR", error)

            }
        })*/
        lifecycleScope.launchWhenStarted {
            viewModel.submitAction(ArticleIntents.RetrieveArticles(FilterType.MONTHLY.value))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      /*  bindViews(view)
        setupRecyclerView()*/
    }

    /*private fun bindViews(view: View) {
        rvArticles = view.findViewById<RecyclerView>(R.id.rv_articles)
        progress = view.findViewById<ProgressBar>(R.id.progress_bar)
    }

    private fun setupRecyclerView() {
        rvArticles.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            articleAdapter = ArticlesAdapter(
                mutableListOf<Article>(),
                SendSingleItemListener {
                    findNavController().navigate(
                        ArticlesListFragmentDirections.actionArticlesListFragmentToDetailsFragment(
                            it
                        )
                    )
                })
            adapter = articleAdapter
        }
    }*/


}