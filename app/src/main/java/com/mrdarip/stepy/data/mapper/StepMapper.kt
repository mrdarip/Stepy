package com.mrdarip.stepy.data.mapper

import com.mrdarip.stepy.data.local.entities.StepEntity
import com.mrdarip.stepy.domain.model.Step

fun StepEntity.toDomain(): Step {
    return Step(id = id, name = name, position = position, taskId = taskId, skippable = skippable)
}

fun Step.toEntity(): StepEntity {
    return StepEntity(
        id = id, name = name, position = position, taskId = taskId, skippable = skippable
    )
}