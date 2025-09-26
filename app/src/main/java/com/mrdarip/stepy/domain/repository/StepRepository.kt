package com.mrdarip.stepy.domain.repository

import com.mrdarip.stepy.domain.model.Step

interface StepRepository {
    suspend fun upsertSteps(steps: List<Step>)
}