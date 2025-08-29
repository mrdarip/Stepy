package com.mrdarip.stepy.data.repository

import com.mrdarip.stepy.data.local.dao.RoutineDao
import com.mrdarip.stepy.data.mapper.toDomain
import com.mrdarip.stepy.data.mapper.toEntity
import com.mrdarip.stepy.domain.model.Routine
import com.mrdarip.stepy.domain.repository.RoutineRepository

class RoutineRepositoryImpl(
    private val routineDao: RoutineDao
) : RoutineRepository {

    override suspend fun getRoutines(): List<Routine> {
        return routineDao.getAllRoutines().map { it.toDomain() }
    }

    override suspend fun addRoutine(routine: Routine) {
        routineDao.insertRoutine(routine.toEntity())
    }
}