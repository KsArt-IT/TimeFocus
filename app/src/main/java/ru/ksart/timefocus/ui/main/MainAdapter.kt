package ru.ksart.timefocus.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.ksart.timefocus.ui.actions.ActionsEditFragment
import ru.ksart.timefocus.ui.actions.ActionsFragment
import ru.ksart.timefocus.ui.history.HistoryFragment
import ru.ksart.timefocus.ui.more.MoreFragment
import ru.ksart.timefocus.ui.tasks.TaskFragment

class MainAdapter(parent: Fragment) : FragmentStateAdapter(parent) {

//    private val mapOfFragment = ArrayMap<Int, BaseFragment>()

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActionsFragment()
            1 -> HistoryFragment()
            2 -> TaskFragment()
            3 -> ActionsEditFragment()
            4 -> MoreFragment()
            else -> throw IllegalStateException()
        }
    }

/*
    override fun containsItem(itemId: Long): Boolean {
        var isContains = false
        mapOfFragment.values.forEach {
            if (it.pageId == itemId) {
                isContains = true
                return@forEach
            }
        }
        return isContains
    }

    override fun getItemId(position: Int) =
        mapOfFragment[position]?.pageId ?: super.getItemId(position)
*/

    override fun getItemCount() = PAGE_COUNT

    private companion object {
        const val PAGE_COUNT = 5
    }
}
