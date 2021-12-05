package ru.ksart.timefocus.data.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.ksart.timefocus.di.IoDispatcher
import ru.ksart.timefocus.domain.entities.IconChoice
import ru.ksart.timefocus.domain.repositories.IconsRepository
import java.io.File
import javax.inject.Inject

class IconsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : IconsRepository {

    private val assetManager = context.assets

    override suspend fun requestIcons(): List<IconChoice> = withContext(dispatcher) {
//        Timber.tag("tag153").d("IconsChoiceRepositoryImpl: requestIcons")
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
