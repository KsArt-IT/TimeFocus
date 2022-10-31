package ru.ksart.timefocus.data.entities

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.ksart.timefocus.R
import ru.ksart.timefocus.domain.entities.PreferencesHelper
import javax.inject.Inject

class PreferencesHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferences: SharedPreferences,
) : PreferencesHelper {

    private val resources = context.resources

    override val isDarkTheme: Boolean
        get() = preferences.getBoolean(
            context.resources.getString(R.string.settings_dark_theme_key),
            context.resources.getBoolean(R.bool.settings_dark_theme_switch_value)
        )
}
