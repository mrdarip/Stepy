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
        stepDao.negateStepsOf(task.id)

        stepDao.upsertSteps(steps.map { it.toEntity() })
    }
}