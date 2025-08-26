package com.mrdarip.stepy.di

import com.mrdarip.stepy.data.local.dao.TaskDao
import com.mrdarip.stepy.data.repository.TaskRepositoryImpl
import com.mrdarip.stepy.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }
}
