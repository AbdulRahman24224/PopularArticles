package com.example.populararticles

import androidx.lifecycle.viewModelScope
import com.example.populararticles.base.viewmodel.StatefulIntentViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before


abstract class StatefulIntentViewModelTest<Intent, State, ViewModel : StatefulIntentViewModel<Intent, State>> {

    val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    protected fun test(
            viewModel: ViewModel,
            intents: List<Intent>,
            assertions: List<State>,
            assertInitialState: Boolean = false
    ): Unit = runBlockingTest {
        val stateCollectionList = if (assertInitialState) mutableListOf(viewModel.initialState)
        else mutableListOf()
        val stateCollectionJob =   viewModel.viewModelScope.launch { viewModel.states.toList(stateCollectionList) }

        intents.forEach { intent -> viewModel.send(intent)}

        assertEquals(assertions.size, stateCollectionList.size)
        assertions.zip(stateCollectionList) { assertion, state ->
            assertEquals(assertion, state)

                stateCollectionJob.cancel()
    }

    @After
    fun cleanUp () {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    }
}


