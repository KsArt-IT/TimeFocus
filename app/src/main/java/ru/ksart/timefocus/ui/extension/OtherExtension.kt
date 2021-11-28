package ru.ksart.timefocus.ui.extension

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

// проверка для when и sealed class на вхождение всех членов класса
val <T> T.exhaustive: T
    get() = this

// загрузка картинки из assets
fun Context.assetsBitmap(path: String): Bitmap? = assets.open(path).use {
    BitmapFactory.decodeStream(it)
}

val Context.isDarkTheme
    get() = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES

fun ImageView.loadSvgFromAsset(name: String) {
    loadSvg(this.context, "file:///android_asset/icons/$name")
}

fun ImageView.loadSvg(context: Context, file: String) {
    val imageLoader = ImageLoader.Builder(context)
        .componentRegistry {
            add(SvgDecoder(context))
        }
        .build()

    val request = ImageRequest.Builder(context)
        .data(file)
//                .crossfade(true)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}
