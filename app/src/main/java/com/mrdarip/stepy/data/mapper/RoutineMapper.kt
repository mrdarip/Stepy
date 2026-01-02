package com.mrdarip.stepy.data.mapper

import com.mrdarip.stepy.data.local.entities.RoutineEntity
import com.mrdarip.stepy.domain.model.Routine

fun Routine.toEntity(): RoutineEntity {
    return RoutineEntity(id = id, name = name, favorite = favorite)
}

fun RoutineEntity.toDomain(): Routine {
    return Routine(id = id, name = name, favorite = favorite)
}