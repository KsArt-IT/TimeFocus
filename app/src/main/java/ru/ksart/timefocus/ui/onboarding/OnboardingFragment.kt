package ru.ksart.timefocus.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ksart.timefocus.R
import ru.ksart.timefocus.databinding.FragmentOnboardingBinding
import ru.ksart.timefocus.model.data.DepthTransformation
import ru.ksart.timefocus.model.data.OnboardingScreen
import ru.ksart.timefocus.model.data.UiState
import ru.ksart.timefocus.ui.extension.exhaustive
import ru.ksart.timefocus.ui.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel: OnboardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentOnboardingBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag("tag153").d("OnboardingFragment start")

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Timber.tag("tag153").d("state: ${state.javaClass.simpleName}")
                    when (state) {
                        is UiState.Loading -> binding.skipOnboarding.isVisible = !state.isLoading
                        is UiState.Next -> showMainFragment()
                        is UiState.Success -> state.data.let(::showOnboarding)
                        is UiState.Error -> {
                            state.message?.let { toast(it) }
                            showMainFragment()
                        }
                    }.exhaustive
                }
            }
        }
        binding.skipOnboarding.setOnClickListener { showMainFragment() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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
