package ru.ksart.timefocus.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import ru.ksart.timefocus.R
import ru.ksart.timefocus.databinding.FragmentMainBinding
import ru.ksart.timefocus.ui.extension.toast
import ru.ksart.timefocus.ui.main.adapter.MainAdapter
import timber.log.Timber
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private var backPressedHold = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag("tag153").d("MainFragment Start")
        initBottomNavigation()
        initViewPager()
        initBack()
    }

    private fun initBack() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            if (backPressedHold + 3000 > System.currentTimeMillis()) {
                // выход из приложения
                activity?.finish()
                exitProcess(0)
            } else {
                toast(R.string.press_back_exit)
                backPressedHold = System.currentTimeMillis()
            }
        }
    }

    private fun showPage(page: Int) {
        Timber.tag("tag153").d("MainFragment: showPage $page")
        if (binding.viewPager.currentItem != page) {
            // TODO: параметр для проверки с пролистыванием или без
            binding.viewPager.currentItem = page
//            binding.viewPager.setCurrentItem(page, false)
        }
    }

    private fun initBottomNavigation() {
        Timber.tag("tag153").d("MainFragment: initBottomNavigation")
        binding.bottomNavigation.run {
            // нажатие на пункт меню
            setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.actionsFragment -> {
                        showPage(0)
                        true
                    }
                    R.id.historyFragment -> {
                        showPage(1)
                        true
                    }
                    R.id.taskCheckFragment -> {
                        showPage(2)
                        true
                    }
                    R.id.actionAddFragment -> {
                        showPage(3)
                        true
                    }
                    R.id.moreFragment -> {
                        showPage(4)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            // повторное нажатие
            setOnNavigationItemReselectedListener { item ->
                when (item.itemId) {
                    R.id.actionsFragment -> {
                        showPage(0)
                    }
                    R.id.historyFragment -> {
                        showPage(1)
                    }
                    R.id.taskCheckFragment -> {
                        showPage(2)
                    }
                    R.id.actionAddFragment -> {
                        showPage(3)
                    }
                    R.id.moreFragment -> {
                        showPage(4)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initViewPager() {
        Timber.tag("tag153").d("MainFragment: initViewPager")
        binding.viewPager.run {
            adapter = MainAdapter(this@MainFragment)
            // сколько страниц кешировать, 1 значит будет еще 2 предсозданы и готовы к отображению, с права и с лева
            offscreenPageLimit = 2
            // ориентация пролистывания, вертикально или горизонтально
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            // создадим трансформацию и добавим трансформацию к viewpager
//            setPageTransformer(DepthTransformation())
            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        Timber.tag("tag153").d("MainFragment: initViewPager $position")
                        if (binding.bottomNavigation.menu.getItem(position).isChecked.not())
                            binding.bottomNavigation.menu.getItem(position).isChecked = true
                    }
                }
            )
        }
    }

}
