package com.example.populararticles.base.compose.preference

import kotlinx.coroutines.flow.Flow

interface Preferences {

    fun setup()

    var theme: Theme
    fun observeTheme(): Flow<Theme>

    var useLessData: Boolean
    fun observeUseLessData(): Flow<Boolean>

    enum class Theme(name : String) {
        LIGHT("light"),
        DARK("dark"),
        SYSTEM("system")
    }
}

