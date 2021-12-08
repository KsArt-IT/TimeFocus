package ru.ksart.timefocus.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import ru.ksart.timefocus.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_settings)

        val darkThemeSwitch =
            findPreference<SwitchPreferenceCompat>(getString(R.string.settings_dark_theme_key))
        darkThemeSwitch?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue == true) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            true
        }
    }
}
