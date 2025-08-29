package com.mrdarip.stepy.domain.repository

import com.mrdarip.stepy.domain.model.Routine

interface RoutineRepository {
    suspend fun getRoutines(): List<Routine>
    suspend fun addRoutine(routine: Routine)
}