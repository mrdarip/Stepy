package com.mrdarip.stepy.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(
    primaryKeys = ["routineId", "stepId"],
    foreignKeys = [
        ForeignKey(
            entity = RoutineEntity::class,
            parentColumns = ["id"],
            childColumns = ["routineId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("routineId"), Index("stepId")]
)
data class RoutineTaskCR(
    val routineId: Int,
    val taskId: Int
)