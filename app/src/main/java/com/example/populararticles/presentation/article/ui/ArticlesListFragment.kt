package com.example.populararticles.presentation.article.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.populararticles.R
import com.example.populararticles.presentation.article.ArticleIntents
import com.example.populararticles.presentation.article.ArticlesState
import com.example.populararticles.presentation.article.ArticlesViewModel
import com.example.populararticles.entities.Article
import com.example.populararticles.presentation.article.SendSingleItemListener

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ArticlesActivity]
 * in two-pane mode (on tablets) or a [ArticleDetailsActivity]
 * on handsets.
 */


class ArticlesListFragment : Fragment() {

    lateinit var viewModel: ArticlesViewModel
    lateinit var articleAdapter: ArticlesAdapter
    lateinit var rvArticles: RecyclerView
    lateinit var progress: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_articles_list, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        viewModel.uiState.onEach { observeState(it) }.launchIn(lifecycleScope)
        lifecycleScope.launchWhenStarted {
            viewModel.intents.send(ArticleIntents.RetrieveArticles(FilterType.MONTHLY.value))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setupRecyclerView()
    }

    private fun bindViews(view: View) {
        rvArticles = view.findViewById<RecyclerView>(R.id.rv_articles)
        progress = view.findViewById<ProgressBar>(R.id.progress_bar)
    }

    private fun setupRecyclerView() {
        rvArticles.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            articleAdapter = ArticlesAdapter(viewModel.uiState.value.articles,
                SendSingleItemListener {
                    findNavController().navigate(
                        ArticlesListFragmentDirections.actionArticlesListFragmentToDetailsFragment(
                            it
                        )
                    )
                })
            adapter = articleAdapter
        }
    }

    private fun observeState(state: ArticlesState) {
        when (state) {
            ArticlesState.Idle -> {

            }
            ArticlesState.Loading -> {
                showProgress()
            }
            is ArticlesState.SuccessArticles -> {
                hideProgress()
                articleAdapter.updateList(state.articles.toMutableList())
            }

            is ArticlesState.Error -> {
                hideProgress()
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                Log.e("ERROR", state.error.toString())
            }
        }
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }


}