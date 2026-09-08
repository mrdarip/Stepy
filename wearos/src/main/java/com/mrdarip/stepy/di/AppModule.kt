package com.mrdarip.stepy.di

import android.app.Application
import com.mrdarip.stepy.data.WearExecutionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWearExecutionRepository(app: Application): WearExecutionRepository {
        return WearExecutionRepository(app)
    }
}
