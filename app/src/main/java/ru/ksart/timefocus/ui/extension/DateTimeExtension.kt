package ru.ksart.timefocus.ui.extension

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

// форматирование даты и времени
private val timeHHmmFormatter = DateTimeFormatter.ofPattern("HH:mm")
    .withZone(ZoneId.systemDefault())
private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    .withZone(ZoneId.systemDefault())
private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    .withZone(ZoneId.systemDefault())
private val viewDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
    .withZone(ZoneId.systemDefault())
private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    .withZone(ZoneId.systemDefault())

/*
private val dateTimeShortFormatter: DateTimeFormatter =
    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
//    .withLocale(Locale.UK)
        .withZone(ZoneId.systemDefault())
*/

fun Instant.toDateFormat() = viewDateFormatter.format(this)

fun Instant.toTimeFormat() = timeHHmmFormatter.format(this)

fun Instant.toMidnightToday(): Instant {
    val t = dateTimeFormatter.parse("${dateFormatter.format(this)} 00:00:00")
    return Instant.from(t)
}

fun Instant.toMidnightTomorrow(): Instant {
    val t = dateTimeFormatter.parse("${dateFormatter.format(this.plusDay())} 00:00:00")
    return Instant.from(t)
}

fun Instant.plusDay(day: Int = 1): Instant = this.plusSeconds(day * SECONDS_IN_DAY)

fun Instant.minusDay(day: Int = 1): Instant = this.minusSeconds(day * SECONDS_IN_DAY)

//
const val TIMER_INTERVAL = 1000L
const val START_TIME = "00:00:00"
private const val TO_SECONDS = 1000
private const val SECONDS_IN_DAY = 86400L


fun Long.toTimeDisplay(): String {
    val h = this / 3600
    val m = this % 3600 / 60
    return "${displaySlot(h)}:${displaySlot(m)}"
}

fun Long.displayTime(): String {
    if (this <= 0L) return START_TIME

    val time = (if (this % TO_SECONDS == 0L) this else this + TO_SECONDS) / TO_SECONDS
    val h = time / 3600
    val m = time % 3600 / 60
    val s = time % 60

    return "${displaySlot(h)}:${displaySlot(m)}:${displaySlot(s)}"
}

private fun displaySlot(count: Long): String {
    return if (count / 10L > 0) {
        "$count"
    } else {
        "0$count"
    }
}
