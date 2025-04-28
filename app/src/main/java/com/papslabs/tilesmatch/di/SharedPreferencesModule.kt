package com.papslabs.tilesmatch.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.papslabs.tilesmatch.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences(Constants.TILES_MATCH_SHARED_PREFS, Context.MODE_PRIVATE)

}