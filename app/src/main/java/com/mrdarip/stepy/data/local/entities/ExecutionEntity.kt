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
    indices = [
        Index(value = ["parentExecutionId", "position"], unique = true),
        Index(value = ["parentRoutineId"])
    ]
)
data class ExecutionEntity(
    @PrimaryKey(true) val id: Long = 0,
    val parentRoutineId: Long?,
    val parentExecutionId: Long?,
    val stepId: Long,
    val position: Int,
    val start: Long,
    val end: Long,
)