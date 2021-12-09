package ru.ksart.timefocus.ui.actions_edit

import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.domain.usecase.actions_edit.CreateActionNamesUseCase
import ru.ksart.timefocus.domain.usecase.actions_edit.GetActionNamesByGroupIdUseCase
import ru.ksart.timefocus.domain.usecase.actions_edit.GetActionNamesByNameUseCase
import ru.ksart.timefocus.domain.usecase.actions_edit.GetActionNamesWithoutGroupUseCase
import ru.ksart.timefocus.domain.usecase.actions_edit.RemoveGroupIdFromActionNamesByGroupIdUseCase
import ru.ksart.timefocus.domain.usecase.actions_edit.UpdateActionNamesUseCase

class ActionsEditViewModelTest {

    @MockK
    lateinit var createActionNames: CreateActionNamesUseCase

    @MockK
    lateinit var updateActionNames: UpdateActionNamesUseCase

    @MockK
    lateinit var getActionNamesWithoutGroup: GetActionNamesWithoutGroupUseCase

    @MockK
    lateinit var getActionNamesByGroupId: GetActionNamesByGroupIdUseCase

    @MockK
    lateinit var getActionNamesByName: GetActionNamesByNameUseCase

    @MockK
    lateinit var removeGroupIdFromActionNamesByGroupId: RemoveGroupIdFromActionNamesByGroupIdUseCase

    @SpyK
    @InjectMockKs
    lateinit var viewModel: ActionsEditViewModel

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
    fun `ActionsEditViewModel init uiState, returns ok`() {
        var actionNames0 = ActionNames(name = "", icon = "")
        var actionNames1 = ActionNames(id = 1, name = "test", icon = "test")
        var actionNames2 = ActionNames(id = 0, name = "test", icon = "test")

        val success0 = UiState.Success(actionNames0)
        val success1 = UiState.Success(actionNames1)
        val success2 = UiState.Success(actionNames2)

        assertEquals(success0, viewModel.uiState.value)
        viewModel.setActionName(actionNames1)
        assertEquals(success2, viewModel.uiState.value)
//        viewModel.setActionName(actionNames1, init = true)
//        assertEquals(success1, viewModel.uiState.value)

    }

    @Test
    fun `ActionsEditViewModel uiState change icon, returns ok`() {
        var actionNames = ActionNames(name = "", icon = "test")

        val success = UiState.Success(actionNames)

        viewModel.setIconFile("test")
        assertEquals(success, viewModel.uiState.value)

    }

}
