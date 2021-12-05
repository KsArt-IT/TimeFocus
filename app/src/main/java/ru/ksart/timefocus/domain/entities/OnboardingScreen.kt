package ru.ksart.timefocus.domain.entities

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnboardingScreen(
    @StringRes val title: Int,
    @StringRes val text: Int,
    @DrawableRes val image: Int,
) : Parcelable
