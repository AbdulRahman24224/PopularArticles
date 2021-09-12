package com.example.populararticles.presentation.articles


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.populararticles.MainCoroutineRule
import com.example.populararticles.domain.repository.ArticlesRepository
import com.example.populararticles.entities.Article
import com.example.populararticles.entities.ArticlesResponse
import com.example.populararticles.getOrAwaitValue
import com.example.populararticles.observeForTesting
import com.example.populararticles.presentation.articles.viewmodel.ArticleIntents
import com.example.populararticles.presentation.articles.viewmodel.HomeViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val defaultDispatcher = TestCoroutineDispatcher()
    private val viewModelScope = TestCoroutineScope(defaultDispatcher)

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var homeViewModel: HomeViewModel

    // prepare mocks
  private  val articleMock = mockk<Article>()
  private  val articlesList = mutableListOf<Article>(articleMock)
  private  val articlesRespopnseSuccessMock = ArticlesResponse(results = articlesList)
  private  val articlesRespopnseFailureMock = ArticlesResponse(status = "false" , error = "invalid input")

  private  val repositoryMock = mockk<ArticlesRepository>() {
        every { this@mockk.getArticlesWithin("0") } returns flow { emit(articlesRespopnseSuccessMock) }
        every { this@mockk.getArticlesWithin("-1") } returns flow { emit(articlesRespopnseFailureMock) }
    }


    @Before
    fun setupViewModel() {

        homeViewModel =  HomeViewModel(
            articleRepository = repositoryMock,
            dispatcher = coroutineRule.dispatcher
        )
    }



    @Test
    fun `when getArticlesWithin succeeds called state goes Loading then returns data`() {

        coroutineRule.runBlockingTest {

            with(homeViewModel) {

                Assert.assertTrue(liveData.value?.articles.isNullOrEmpty())

                submitAction(ArticleIntents.RetrieveArticles("0"))

                liveData.observeForTesting {
                    Assert.assertTrue(statesList.size==3)
                    Assert.assertTrue(statesList[0].isLoading)
                    Assert.assertTrue(liveData.getOrAwaitValue().articles.isNotEmpty())
                    Assert.assertTrue(liveData.getOrAwaitValue().isLoading.not())
                }

            }
        }

    
    }

    @Test
    fun ` when getArticlesWithin fails state goes Loading then state error is Not Empty   `() {

        coroutineRule.runBlockingTest {

            with(homeViewModel) {

                Assert.assertTrue(liveData.value?.error.isNullOrEmpty())

                submitAction(ArticleIntents.RetrieveArticles("-1"))

                liveData.observeForTesting {

                    Assert.assertTrue(statesList.size==3)
                    Assert.assertTrue(statesList[0].isLoading)

                    val finalState =  liveData.getOrAwaitValue()
                    Assert.assertTrue(finalState.error.isNotEmpty())
                    Assert.assertTrue(finalState.isLoading.not())
                }

            }
        }


    }


}