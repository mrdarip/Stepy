package com.mrdarip.stepy.domain.model

data class Execution(
    val id: Long = 0,
    val parentRoutineId: Long?,
    val parentExecutionId: Long?,
    val stepId: Long,
    val position: Int,
    val start: Long,
    val end: Long,
    val skipped: Boolean
)
