package com.mrdarip.stepy.data

data class WearExecutionState(
    val taskId: Long,
    val taskName: String,
    val currentStepName: String,
    val currentStepPosition: Int,
    val totalSteps: Int,
    val executionStart: Long,
    val upperBoundEta: Long
)
