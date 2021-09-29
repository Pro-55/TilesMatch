package com.example.tilesmatch.di

import android.app.Application
import android.content.res.AssetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AssetManagerModule {

    @ViewModelScoped
    @Provides
    fun provideAssetManager(application: Application): AssetManager = application.assets

}