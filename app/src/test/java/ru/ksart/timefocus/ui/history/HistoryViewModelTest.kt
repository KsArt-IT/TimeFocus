package ru.ksart.timefocus.ui.history

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.domain.usecase.actions.GetActionHistoryUseCase

class HistoryViewModelTest {
    @MockK
    lateinit var getActionHistory: GetActionHistoryUseCase

    @SpyK
    @InjectMockKs
    lateinit var viewModel: HistoryViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUpDispatcher() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDownDispatcher() = Dispatchers.resetMain()

    @Before
    fun setUpMockK() = MockKAnnotations.init(this)

    @After
    fun tearDownMockK() = unmockkAll()

    @Test
    fun `HistoryViewModelTest init uiState, returns ok`() {
        val loading = UiState.Loading
        assertEquals(loading, viewModel.uiState.value)
/*
        runBlocking {
            val loading = UiState.Loading
            val today = Instant.now()
            val history = HistoryActions(intervals = listOf(), date = "", timeSum = "00:01")

            assertEquals(loading, viewModel.uiState.value)
            coEvery { getActionHistory.observe(today) } returns flow { Results.Success(history) }
            coVerify { getActionHistory.observe(today) }

        }
*/
    }

}
