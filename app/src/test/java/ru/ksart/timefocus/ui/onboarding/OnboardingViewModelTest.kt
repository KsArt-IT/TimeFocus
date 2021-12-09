package ru.ksart.timefocus.ui.onboarding

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
import ru.ksart.timefocus.domain.entities.OnboardingScreen
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.usecase.onboarding.CheckStartFirstUseCase
import ru.ksart.timefocus.domain.usecase.onboarding.GetOnboardingScreensUseCase
import ru.ksart.timefocus.domain.usecase.onboarding.InitDataUseCase

class OnboardingViewModelTest {
    @MockK
    lateinit var getOnboardingScreens: GetOnboardingScreensUseCase

    @MockK
    lateinit var checkStartFirst: CheckStartFirstUseCase

    @MockK
    lateinit var initData: InitDataUseCase

    @SpyK
    @InjectMockKs
    lateinit var viewModel: OnboardingViewModel

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
    fun `OnboardingViewModel init uiState, returns ok`() {
        val loading = UiState.Loading
        assertEquals(loading, viewModel.uiState.value)
/*
        runBlocking {
            val loading = UiState.Loading
            val list = Results.Success(emptyList<OnboardingScreen>())

            assertEquals(loading, viewModel.uiState.value)
            coEvery { getOnboardingScreens() } returns list
            coVerify { getOnboardingScreens() }

        }
*/
    }

}
