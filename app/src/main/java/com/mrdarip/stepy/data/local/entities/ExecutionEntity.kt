package com.mrdarip.stepy.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "entities",
    foreignKeys = [
        ForeignKey(
            entity = ExecutionEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentExecutionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RoutineEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentRoutineId"],
            onDelete = ForeignKey.SET_NULL
        )

    ],
    indices = [Index(value = ["parentExecutionId", "position"], unique = true)]
)
data class ExecutionEntity(
    @PrimaryKey(true) val id: Int = 0,
    val parentRoutineId: Int?,
    val parentExecutionId: Int?,
    val stepId: Int,
    val position: Int,
    val start: Int,
    val end: Int,
)