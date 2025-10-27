package com.mrdarip.stepy.domain.repository

import com.mrdarip.stepy.domain.model.Step
import com.mrdarip.stepy.domain.model.Task

interface StepRepository {

    /**
     * Rebuilds the steps of a given [Task].
     *
     * This function replaces the existing steps of the task with the provided [Step] list.
     *
     * **Warning:** This operation is destructive. Any existing steps that are not included
     * in the new list will become unused.
     *
     * @param steps The new list of steps to set for the task.
     * @param task The task whose steps will be rebuilt.
     */
    suspend fun rebuildTaskSteps(steps: List<Step>, task: Task)
}