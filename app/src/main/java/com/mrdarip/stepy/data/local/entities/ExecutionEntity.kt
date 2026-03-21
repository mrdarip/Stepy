package com.mrdarip.stepy.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Execution entity in the database.
 *
 * @property id The unique identifier for the execution entity. Auto-generated
 * @property parentRoutineId ID of the routine this execution belongs to. Null if not a routine execution
 * @property parentExecutionId ID of the parent execution this execution is related to. Null if not a child execution (first execution)
 * @property stepId ID of the step associated with this execution.
 * @property position The position of this execution in the sequence.
 * @property start The start time of the execution, represented as a timestamp in seconds
 * @property end The end time of the execution, represented as a timestamp in seconds
 * @property skipped A flag indicating whether the execution was skipped.
 */
@Entity(
    tableName = "executions",
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
    val skipped: Boolean
)