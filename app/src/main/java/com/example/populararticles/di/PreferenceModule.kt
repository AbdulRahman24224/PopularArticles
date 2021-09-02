package com.example.populararticles.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.example.populararticles.base.compose.preference.Preferences
import com.example.populararticles.base.compose.preference.PreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class SettingsModuleBinds {
    @Singleton
    @Binds
    abstract fun providePreferences(bind: PreferencesImpl): Preferences
}

@InstallIn(SingletonComponent::class)
@Module
internal object SettingsModule {
    @Named("app")
    @Provides
    @Singleton
    fun provideAppPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}
