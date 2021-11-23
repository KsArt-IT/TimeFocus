package ru.ksart.timefocus.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.ksart.timefocus.R
import ru.ksart.timefocus.databinding.FragmentOnboardingScreenBinding
import ru.ksart.timefocus.model.data.OnboardingScreen
import timber.log.Timber

class OnboardingScreenFragment : Fragment() {

    private var _binding: FragmentOnboardingScreenBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentOnboardingScreenBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screen = arguments?.get(KEY_SCREEN)?.let { it as? OnboardingScreen }
        Timber.tag("tag153").d("OnboardingScreenFragment screen=${screen?.title}")
        binding.run {
            imageOnboarding.setImageResource(screen?.image ?: R.drawable.ic_error_24)
            titleOnboarding.setText(screen?.title ?: R.string.empty)
            textOnboarding.setText(screen?.text ?: R.string.empty)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        private const val KEY_SCREEN = "onboarding_screen"

        @JvmStatic
        fun newInstance(
            screen: OnboardingScreen,
        ): OnboardingScreenFragment {
            return OnboardingScreenFragment().apply {
                arguments = bundleOf(
                    KEY_SCREEN to screen,
                )
            }
        }
    }
}
