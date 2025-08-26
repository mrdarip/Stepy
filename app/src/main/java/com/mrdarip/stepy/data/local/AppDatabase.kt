package com.mrdarip.stepy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrdarip.stepy.data.local.dao.ExecutionDao
import com.mrdarip.stepy.data.local.dao.RoutineDao
import com.mrdarip.stepy.data.local.dao.RoutineTaskCRDao
import com.mrdarip.stepy.data.local.dao.StepDao
import com.mrdarip.stepy.data.local.dao.TaskDao
import com.mrdarip.stepy.data.local.entities.ExecutionEntity
import com.mrdarip.stepy.data.local.entities.RoutineEntity
import com.mrdarip.stepy.data.local.entities.RoutineTaskCR
import com.mrdarip.stepy.data.local.entities.StepEntity
import com.mrdarip.stepy.data.local.entities.TaskEntity

@Database(
    entities = [ExecutionEntity::class, RoutineEntity::class, StepEntity::class, TaskEntity::class, RoutineTaskCR::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun executionDao(): ExecutionDao
    abstract fun routineDao(): RoutineDao
    abstract fun stepDao(): StepDao
    abstract fun taskDao(): TaskDao
    abstract fun routineTaskCRDao(): RoutineTaskCRDao
}
