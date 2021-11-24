package ru.ksart.timefocus.repositories

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.ksart.timefocus.R
import ru.ksart.timefocus.model.db.ActionsDatabase
import timber.log.Timber
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : SettingsRepository {

    // для PreferenceManager используем lazy,
    // чтобы в последствии первый раз обратиться к переменной на потоке Dispatchers.IO
    private val defaultPreferences by lazy {
        context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
    }

    @Inject
    lateinit var db: ActionsDatabase

    private val res = context.resources

    // ключи для чтения параметров sharedPref
    private val firstStartKey: String by lazy { res.getString(R.string.first_start_key) }

    override suspend fun checkStartFirst() = withContext(Dispatchers.IO) {
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
        db.actionNamesDao().getActionNames()
    }
}
