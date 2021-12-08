package ru.ksart.timefocus.data.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import ru.ksart.timefocus.R
import ru.ksart.timefocus.data.db.ActionsDatabase
import ru.ksart.timefocus.di.IoDispatcher
import ru.ksart.timefocus.domain.entities.AppSettings
import ru.ksart.timefocus.domain.repositories.SettingsRepository
import timber.log.Timber
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val db: ActionsDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : SettingsRepository {

    private val _changeSettings = MutableStateFlow(false)
    override val changeSettings = _changeSettings.asStateFlow()

    // для PreferenceManager используем lazy,
    // чтобы в последствии первый раз обратиться к переменной на потоке Dispatchers.IO
    private val defaultPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
//        context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
    }

    private val listener by lazy {
        SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            _changeSettings.value = !_changeSettings.value
        }
    }

    private val res = context.resources

    // ключи для чтения параметров sharedPref
    private val firstStartKey: String by lazy { res.getString(R.string.first_start_key) }
    private val darkThemKey: String by lazy { res.getString(R.string.settings_dark_theme_key) }

    override fun changeTheme() {
        val isDarkTheme = defaultPreferences.getBoolean(
            darkThemKey,
            res.getBoolean(R.bool.settings_dark_theme_switch_value)
        )
        if (isDarkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override suspend fun registerChangeSettings() {
        withContext(Dispatchers.IO) {
            defaultPreferences.registerOnSharedPreferenceChangeListener(listener)
        }
    }

    override suspend fun unregisterChangeSettings() {
        withContext(Dispatchers.IO) {
            defaultPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    override suspend fun readSettings(): AppSettings = withContext(Dispatchers.IO) {
        AppSettings(
            darkTheme = defaultPreferences.getBoolean(
                darkThemKey,
                res.getBoolean(R.bool.settings_dark_theme_switch_value)
            )
        )
    }

    override suspend fun checkStartFirst() = withContext(dispatcher) {
        Timber.tag("tag153").d("checkStartFirst")
        defaultPreferences.run {
            getBoolean(firstStartKey, true).also {
                Timber.tag("tag153").d("checkStartFirst $it")
                if (it) edit { putBoolean(firstStartKey, false) }
            }
        }
    }

    override suspend fun initData() {
        //TODO init data
        db.actionNamesDao().actionNames()
    }
}
