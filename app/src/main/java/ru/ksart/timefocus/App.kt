package ru.ksart.timefocus

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import ru.ksart.timefocus.domain.usecase.settings.ChangeThemeUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        setTheme()
        super.onCreate()
        AndroidThreeTen.init(this)
        Timber.plant(Timber.DebugTree())
    }

    private fun setTheme() {
        val isDarkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
            getString(R.string.settings_dark_theme_key),
            this.resources.getBoolean(R.bool.settings_dark_theme_switch_value)
        )
        if (isDarkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}
