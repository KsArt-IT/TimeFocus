package ru.ksart.timefocus.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ksart.timefocus.model.data.IconChoice
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class IconsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : IconsRepository {

    private val assetManager = context.assets

    override suspend fun requestIcons(): List<IconChoice> = withContext(Dispatchers.IO) {
        Timber.tag("tag153").d("IconsChoiceRepositoryImpl: requestIcons")
//        val dir = File("file:///android_asset/icons/")
        var count = 0L
//        val files = dir.listFiles()?.filter { it.isFile }?.map { file ->
        val files = assetManager.list("icons")?.map { file ->
            IconChoice(
                id = count++,
                icon = File(file).name
            )
        } ?: emptyList()
//        Timber.tag("tag153").d("IconsChoiceRepositoryImpl: getIcons=$files")
        files
    }
}
