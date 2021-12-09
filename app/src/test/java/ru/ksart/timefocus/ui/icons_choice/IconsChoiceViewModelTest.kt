package ru.ksart.timefocus.ui.icons_choice

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.domain.entities.IconChoice
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.usecase.icons.GetIconsUseCase
import kotlin.time.ExperimentalTime

@ExperimentalTime
class IconsChoiceViewModelTest {
    @MockK
    lateinit var getIconsUseCase: GetIconsUseCase

    @SpyK
    @InjectMockKs
    lateinit var viewModel: IconsChoiceViewModel

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
    fun `IconsChoiceViewModel get List-IconChoice true`() {
        runBlocking {
            val iconChoice = IconChoice(id = 1, icon = "test_path")
            val list = listOf(iconChoice)
            val loading = UiState.Loading
            val success = Results.Success(list)
            val uiSuccess = UiState.Success(list)

            assertEquals(loading, viewModel.uiState.value)
            coEvery { getIconsUseCase.invoke() } returns success
            coVerify { getIconsUseCase.invoke() }
            assertEquals(loading, viewModel.uiState.value)
/*
            viewModel.uiState.test {
                awaitItem() // initial state value
                assertEquals(loading, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
*/
        }
    }
}
