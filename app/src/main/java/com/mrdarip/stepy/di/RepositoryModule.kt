package com.mrdarip.stepy.di

import com.mrdarip.stepy.data.local.dao.ExecutionDao
import com.mrdarip.stepy.data.local.dao.RoutineDao
import com.mrdarip.stepy.data.local.dao.StepDao
import com.mrdarip.stepy.data.local.dao.TaskDao
import com.mrdarip.stepy.data.repository.ExecutionRepositoryImpl
import com.mrdarip.stepy.data.repository.RoutineRepositoryImpl
import com.mrdarip.stepy.data.repository.StepRepositoryImpl
import com.mrdarip.stepy.data.repository.TaskRepositoryImpl
import com.mrdarip.stepy.domain.repository.ExecutionRepository
import com.mrdarip.stepy.domain.repository.RoutineRepository
import com.mrdarip.stepy.domain.repository.StepRepository
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

    @Provides
    @Singleton
    fun provideRoutineRepository(routineDao: RoutineDao): RoutineRepository {
        return RoutineRepositoryImpl(routineDao)
    }

    @Provides
    @Singleton
    fun provideExecutionRepository(executionDao: ExecutionDao): ExecutionRepository {
        return ExecutionRepositoryImpl(executionDao)
    }

    @Provides
    @Singleton
    fun provideStepRepository(stepDao: StepDao): StepRepository {
        return StepRepositoryImpl(stepDao)
    }
}
