package ru.ksart.timefocus.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.ksart.timefocus.model.data.OnboardingScreen

class OnboardingAdapter(
    private val screens: List<OnboardingScreen>,
    parent: Fragment,
) : FragmentStateAdapter(parent) {

    override fun getItemCount(): Int = screens.size

    override fun createFragment(position: Int): Fragment {
        val screen = screens[position]
        return OnboardingScreenFragment.newInstance(screen)
    }
}
