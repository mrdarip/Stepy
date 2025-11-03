package com.mrdarip.stepy.data.repository

import com.mrdarip.stepy.data.local.dao.StepDao
import com.mrdarip.stepy.data.mapper.toEntity
import com.mrdarip.stepy.domain.model.Step
import com.mrdarip.stepy.domain.model.Task
import com.mrdarip.stepy.domain.repository.StepRepository

class StepRepositoryImpl(
    private val stepDao: StepDao
) : StepRepository {
    override suspend fun rebuildTaskSteps(steps: List<Step>, task: Task) {
        val existingSteps = stepDao.getStepsOfTask(task.id)

        // Ensure all steps belong to the same task
        val newSteps = steps.map { step ->
            if (step.taskId != task.id) {
                throw IllegalArgumentException("Step '${step.name}' with id ${step.id} does not belong to task '${task.name}', with id ${task.id}")
            }
            step.toEntity()
        }

        stepDao.upsertSteps(newSteps)

        val newStepIds = newSteps.map { it.id }.toSet()
        val unusedSteps = existingSteps.filter { it.id !in newStepIds }

        stepDao.setStepsAsUnused(unusedSteps.map { it.id })
    }
}