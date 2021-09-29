package com.example.tilesmatch.di

import android.content.res.AssetManager
import com.example.tilesmatch.data.repository.contract.Repository
import com.example.tilesmatch.data.repository.impl.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(am:AssetManager): Repository = RepositoryImpl(am)

}