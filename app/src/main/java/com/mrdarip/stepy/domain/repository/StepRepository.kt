package com.mrdarip.stepy.domain.repository

import com.mrdarip.stepy.domain.model.Step
import com.mrdarip.stepy.domain.model.Task

interface StepRepository {
    suspend fun rebuildTaskSteps(steps: List<Step>, task: Task)
}