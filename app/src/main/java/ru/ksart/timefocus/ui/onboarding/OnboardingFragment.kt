package ru.ksart.timefocus.ui.onboarding

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.ksart.timefocus.R
import ru.ksart.timefocus.databinding.FragmentOnboardingBinding
import ru.ksart.timefocus.data.entities.DepthTransformation
import ru.ksart.timefocus.domain.entities.OnboardingScreen
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.ui.extension.exhaustive
import ru.ksart.timefocus.ui.extension.toast
import ru.ksart.timefocus.ui.main.BaseFragment
import ru.ksart.timefocus.ui.onboarding.adapter.OnboardingAdapter
import timber.log.Timber

@AndroidEntryPoint
class OnboardingFragment :
    BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    private val viewModel: OnboardingViewModel by viewModels()

    override fun initObserve() {
        Timber.tag("tag153").d("OnboardingFragment start")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvent.collect { event ->
                        Timber.tag("tag153").d("state: ${event.javaClass.simpleName}")
                        when (event) {
                            is UiEvent.Success -> {}
                            is UiEvent.Toast -> toast(event.stringId)
                            is UiEvent.Error -> {
                                toast(event.message)
                                showMainFragment()
                            }
                            is UiEvent.Next -> showMainFragment()
                        }.exhaustive
                    }
                }
                launch {
                    viewModel.uiState.collectLatest { state ->
                        when (state) {
                            is UiState.Success -> showOnboarding(state.data)
                            is UiState.Loading -> {}
                        }.exhaustive
                    }
                }
                launch {
                    viewModel.uiStateSkip.collectLatest { state ->
                        when (state) {
                            is UiState.Success -> showSkipButton(true)
                            is UiState.Loading -> showSkipButton(false)
                        }.exhaustive
                    }
                }
            }
        }
    }

    override fun initListener() {
        binding.skipOnboarding.setOnClickListener { showMainFragment() }
    }

    private fun showSkipButton(show: Boolean) {
        binding.skipOnboarding.isVisible = show
    }

    private fun showOnboarding(list: List<OnboardingScreen>) {
        Timber.tag("tag153").d("showOnboarding list=${list.size}")
        binding.viewPager.run {
            adapter = OnboardingAdapter(list, this@OnboardingFragment)
            // сколько страниц кешировать, 1 значит будет еще 2 предсозданы и готовы к отображению, с права и с лева
            offscreenPageLimit = 2
            // ориентация пролистывания, вертикально или горизонтально
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            // создадим трансформацию и добавим трансформацию к viewpager
            setPageTransformer(DepthTransformation())
            // добавить viewpager к индикатору
            binding.wormDotsIndicator.setViewPager2(this)
        }
    }

    private fun showMainFragment() {
        Timber.tag("tag153").d("showMainFragment")
        findNavController().navigate(R.id.action_onboardingFragment_to_mainFragment)
    }
}
