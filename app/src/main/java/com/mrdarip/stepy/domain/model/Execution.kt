package com.mrdarip.stepy.domain.model

data class Execution(
    val id: Int = 0,
    val parentRoutineId: Int?,
    val parentExecutionId: Int?,
    val stepId: Int,
    val position: Int,
    val start: Int,
    val end: Int
)
