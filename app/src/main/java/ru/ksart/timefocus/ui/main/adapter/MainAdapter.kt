package ru.ksart.timefocus.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.ksart.timefocus.ui.actions.ActionsFragment
import ru.ksart.timefocus.ui.actions_list.ActionsListFragment
import ru.ksart.timefocus.ui.history.HistoryFragment
import ru.ksart.timefocus.ui.more.MoreFragment
import ru.ksart.timefocus.ui.tasks.TaskFragment

class MainAdapter(parent: Fragment) : FragmentStateAdapter(parent) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActionsFragment()
            1 -> HistoryFragment()
            2 -> TaskFragment()
            3 -> ActionsListFragment()
            4 -> MoreFragment()
            else -> error("Unknown fragment")
        }
    }

    override fun getItemCount() = PAGE_COUNT

    private companion object {
        const val PAGE_COUNT = 5
    }
}
