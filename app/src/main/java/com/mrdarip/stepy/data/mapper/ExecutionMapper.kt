package com.mrdarip.stepy.data.mapper

import com.mrdarip.stepy.data.local.entities.ExecutionEntity
import com.mrdarip.stepy.domain.model.Execution

fun ExecutionEntity.toDomain(): Execution {
    return Execution(
        id = id,
        parentRoutineId = parentRoutineId,
        parentExecutionId = parentExecutionId,
        stepId = stepId,
        position = position,
        start = start,
        end = end,
        skipped = skipped
    )
}

fun Execution.toEntity(): ExecutionEntity {
    return ExecutionEntity(
        id = id,
        parentRoutineId = parentRoutineId,
        parentExecutionId = parentExecutionId,
        stepId = stepId,
        position = position,
        start = start,
        end = end,
        skipped = skipped
    )
}