package ru.ksart.timefocus.ui.actions

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
import ru.ksart.timefocus.domain.usecase.actions.CreateActionUseCase
import ru.ksart.timefocus.domain.usecase.actions.GetActionNamesGroupByGroupIdUseCase
import ru.ksart.timefocus.domain.usecase.actions.GetActionNamesWithoutGroupUseCase
import ru.ksart.timefocus.domain.usecase.actions.GetActionsWithInfoUseCase
import ru.ksart.timefocus.domain.usecase.actions.PauseTimerUseCase
import ru.ksart.timefocus.domain.usecase.actions.StartTimerUseCase
import ru.ksart.timefocus.domain.usecase.actions.StopActionUseCase
import ru.ksart.timefocus.domain.usecase.actions.StopTimerUseCase
import ru.ksart.timefocus.domain.usecase.actions.UpdateActionStatusUseCase

class ActionsViewModelTest {

    @MockK
    lateinit var getActionsWithInfo: GetActionsWithInfoUseCase

    @MockK
    lateinit var createAction: CreateActionUseCase

    @MockK
    lateinit var updateActionStatus: UpdateActionStatusUseCase

    @MockK
    lateinit var stopAction: StopActionUseCase

    @MockK
    lateinit var startTimer: StartTimerUseCase

    @MockK
    lateinit var pauseTimer: PauseTimerUseCase

    @MockK
    lateinit var stopTimer: StopTimerUseCase

    @MockK
    lateinit var getActionsNames: GetActionNamesWithoutGroupUseCase

    @MockK
    lateinit var getActionsNamesByGroupId: GetActionNamesGroupByGroupIdUseCase

    @SpyK
    @InjectMockKs
    lateinit var viewModel: ActionsViewModel

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
    fun `ActionsViewModel init uiState, returns ok`() {
        val loading = UiState.Loading

        assertEquals(loading, viewModel.uiState.value)
    }
}
