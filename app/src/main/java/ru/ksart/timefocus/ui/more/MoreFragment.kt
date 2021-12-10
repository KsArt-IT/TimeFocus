package ru.ksart.timefocus.ui.more

import androidx.navigation.fragment.findNavController
import ru.ksart.timefocus.R
import ru.ksart.timefocus.databinding.FragmentMoreBinding
import ru.ksart.timefocus.ui.extension.toast
import ru.ksart.timefocus.ui.main.BaseFragment
import ru.ksart.timefocus.ui.main.MainFragmentDirections

class MoreFragment : BaseFragment<FragmentMoreBinding>(FragmentMoreBinding::inflate) {

    override fun initListener() {
        binding.run {
            settings.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
            }
            help.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToOnboardingFragment(true)
                findNavController().navigate(action)
            }
            about.setOnClickListener {
                toast(R.string.app_about)
            }
        }
    }
}
