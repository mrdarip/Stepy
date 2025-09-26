package com.mrdarip.stepy.data.repository

import com.mrdarip.stepy.data.local.dao.StepDao
import com.mrdarip.stepy.data.mapper.toEntity
import com.mrdarip.stepy.domain.model.Step
import com.mrdarip.stepy.domain.repository.StepRepository

class StepRepositoryImpl(
    private val stepDao: StepDao
) : StepRepository {
    override suspend fun upsertSteps(steps: List<Step>) {
        stepDao.upsertSteps(steps.map { it.toEntity() })
    }
}