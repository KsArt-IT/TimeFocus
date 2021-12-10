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
import ru.ksart.timefocus.domain.usecase.actions.StopActionUseCase
import ru.ksart.timefocus.domain.usecase.actions.UpdateActionStatusUseCase
import ru.ksart.timefocus.domain.usecase.pomodoro.CounterPomodoroUseCase
import ru.ksart.timefocus.domain.usecase.pomodoro.SavePomodoroUseCase
import ru.ksart.timefocus.domain.usecase.pomodoro.StartPomodoroUseCase
import ru.ksart.timefocus.domain.usecase.pomodoro.StopPomodoroUseCase
import ru.ksart.timefocus.domain.usecase.pomodoro.UsePomodoroUseCase
import ru.ksart.timefocus.domain.usecase.water.SaveWaterUseCase
import ru.ksart.timefocus.domain.usecase.water.WaterVolumeUseCase

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
    lateinit var getActionsNames: GetActionNamesWithoutGroupUseCase

    @MockK
    lateinit var getActionsNamesByGroupId: GetActionNamesGroupByGroupIdUseCase

    @MockK
    lateinit var startPomodoro: StartPomodoroUseCase

    @MockK
    lateinit var stopPomodoro: StopPomodoroUseCase

    @MockK
    lateinit var usePomodoro: UsePomodoroUseCase

    @MockK
    lateinit var counterPomodoro: CounterPomodoroUseCase

    @MockK
    lateinit var savePomodoro: SavePomodoroUseCase

    @MockK
    lateinit var waterVolume: WaterVolumeUseCase

    @MockK
    lateinit var saveWaterUseCase: SaveWaterUseCase

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
