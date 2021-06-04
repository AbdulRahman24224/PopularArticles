package com.example.populararticles.utils.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.populararticles.base.compose.preference.Preferences

@Composable
fun Preferences.shouldUseDarkColors(): Boolean {
    val themePreference = observeTheme().collectAsState(initial = Preferences.Theme.SYSTEM)
    return when (themePreference.value) {
        Preferences.Theme.LIGHT -> false
        Preferences.Theme.DARK -> true
        else -> isSystemInDarkTheme()
    }
}