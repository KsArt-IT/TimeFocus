package ru.ksart.timefocus.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.ksart.timefocus.R
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.tag("tag153").d("MainActivity Start")
//        val navController = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
//        navController.navigate(R.id.onboardingFragment)
//        Timber.tag("tag153").d("MainActivity navController=$navController")
    }
}
