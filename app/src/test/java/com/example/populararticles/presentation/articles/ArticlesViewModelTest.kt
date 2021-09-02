package com.example.populararticles.presentation.articles


import com.example.populararticles.domain.repository.ArticlesRepository
import com.example.populararticles.entities.Article
import com.example.populararticles.entities.ArticlesResponse
import com.example.populararticles.presentation.articles.viewmodel.ArticleIntents
import com.example.populararticles.presentation.articles.viewmodel.ArticleStates
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}

class ArticlesViewModelTest {

    private val defaultDispatcher = TestCoroutineDispatcher()
    private val viewModelScope = TestCoroutineScope(defaultDispatcher)
    val articleMock = mockk<Article>()
    val articlesList = mutableListOf<Article>(articleMock)
    val articlesRespopnseSuccessMock = ArticlesResponse(results = articlesList)
    val articlesRespopnseFailureMock = ArticlesResponse(status = "false")
    val repositoryMock = mockk<ArticlesRepository>() {

        every { this@mockk.getArticlesWithin("0") } returns flow { emit(articlesRespopnseSuccessMock) }
        every { this@mockk.getArticlesWithin("-1") } returns flow { emit(articlesRespopnseFailureMock) }
    }

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test
    fun ` when getArticles success called state goes Loading  `() {

        coroutineRule.dispatcher.runBlockingTest {
            with(
                ArticlesViewModel(
                    articleRepository = repositoryMock,
                    defaultDispatcher = coroutineRule.dispatcher
                )
            ) {

                intents.send(com.example.populararticles.presentation.articles.viewmodel.ArticleIntents.RetrieveArticles("0"))
                assertEquals(com.example.populararticles.presentation.articles.viewmodel.ArticleStates.Loading, statesList.get(0))

            }
        }
    }

    @Test
    fun ` when getArticles success after loading state goes SuccessArticles `() {
        runBlockingTest {
            with(
                ArticlesViewModel(
                    articleRepository = repositoryMock,
                    defaultDispatcher = coroutineRule.dispatcher
                )
            ) {
                viewModelScope.launch {
                    intents.send(com.example.populararticles.presentation.articles.viewmodel.ArticleIntents.RetrieveArticles("0"))
                    assertEquals(com.example.populararticles.presentation.articles.viewmodel.ArticleStates.Loading, statesList.get(0))
                    assertEquals(com.example.populararticles.presentation.articles.viewmodel.ArticleStates.SuccessArticles(articlesList), statesList.get(1))
                }

            }
        }

    }

    @Test
    fun ` when getArticles fails state goes Error `() {
        with(
            ArticlesViewModel(
                articleRepository = repositoryMock,
                defaultDispatcher = coroutineRule.dispatcher
            )
        ) {
            viewModelScope.launch {
                intents.send(com.example.populararticles.presentation.articles.viewmodel.ArticleIntents.RetrieveArticles("-1"))
                assertEquals(com.example.populararticles.presentation.articles.viewmodel.ArticleStates.Loading, statesList.get(0))
                assertEquals(com.example.populararticles.presentation.articles.viewmodel.ArticleStates.Error(""), statesList.get(1))
            }

        }
    }


}