package com.mrdarip.stepy.domain.repository

import com.mrdarip.stepy.domain.model.Execution
import com.mrdarip.stepy.domain.model.Routine
import com.mrdarip.stepy.domain.model.Step

interface ExecutionRepository {
    suspend fun addExecution(execution: Execution): Long
    fun createExecutionNow(
        step: Step,
        parentExecution: Execution? = null,
        routine: Routine? = null
    ): Execution
}