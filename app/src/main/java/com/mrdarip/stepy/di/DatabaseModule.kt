package com.mrdarip.stepy.di

import android.app.Application
import androidx.room.Room
import com.mrdarip.stepy.data.local.AppDatabase
import com.mrdarip.stepy.data.local.dao.ExecutionDao
import com.mrdarip.stepy.data.local.dao.RoutineDao
import com.mrdarip.stepy.data.local.dao.RoutineTaskCRDao
import com.mrdarip.stepy.data.local.dao.StepDao
import com.mrdarip.stepy.data.local.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()

    @Provides
    fun provideStepDao(db: AppDatabase): StepDao = db.stepDao()

    @Provides
    fun provideExecutionDao(db: AppDatabase): ExecutionDao = db.executionDao()

    @Provides
    fun provideRoutineDao(db: AppDatabase): RoutineDao = db.routineDao()

    @Provides
    fun provideRoutineTaskCRDao(db: AppDatabase): RoutineTaskCRDao = db.routineTaskCRDao()
}
