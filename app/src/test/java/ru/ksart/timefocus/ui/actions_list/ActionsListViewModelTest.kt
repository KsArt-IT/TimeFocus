package ru.ksart.timefocus.ui.actions_list

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.domain.usecase.actions_list.GetActionNamesListUseCase

class ActionsListViewModelTest {
    @MockK
    lateinit var getActionNamesList: GetActionNamesListUseCase

    @SpyK
    @InjectMockKs
    lateinit var viewModel: ActionsListViewModel

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
    fun `ActionsListViewModelTest init uiState, returns ok`() {
        runBlocking {
            val loading = UiState.Loading
            val list = listOf(ActionNames(name = "test", icon = "test_path"))

            assertEquals(loading, viewModel.uiState.value)
            coEvery { getActionNamesList.observe() } returns flow { list }
            coVerify { getActionNamesList.observe() }

        }
    }
}
