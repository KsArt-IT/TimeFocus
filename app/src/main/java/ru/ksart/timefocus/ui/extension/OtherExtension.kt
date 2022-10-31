package ru.ksart.timefocus.ui.extension

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

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
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    val request = ImageRequest.Builder(context)
        .data(file)
//                .crossfade(true)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

inline fun <T> View.doAsync(
    crossinline backgroundTask: (scope: CoroutineScope) -> T,
    crossinline result: (T?) -> Unit
) {
    val job = CoroutineScope(Dispatchers.Main)
    // Добавляем слушатель, который будет отменять
    // корутину, если вьюха откреплена
    val attachListener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {}

        override fun onViewDetachedFromWindow(v: View) {
            job.cancel()
            removeOnAttachStateChangeListener(this)
        }
    }
    this.addOnAttachStateChangeListener(attachListener)
    // Создаем Job, которая будет работать в основном потоке
    job.launch {
        // Создаем Deferred с результатом в фоновом потоке
        val data = async(Dispatchers.Default) {
            try {
                backgroundTask(this)
            } catch (e: Exception) {
                e.printStackTrace()
                return@async null
            }
        }
        if (isActive) {
            try {
                result.invoke(data.await())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // Отписываем слушатель по окончании Job
        this@doAsync.removeOnAttachStateChangeListener(attachListener)
    }
}
